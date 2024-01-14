package com.sms.service.impl;


import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sms.common.Result;
import com.sms.common.SmsEnableConfig;
import com.sms.dto.PwdDto;
import com.sms.dto.PwdForgetDto;
import com.sms.dto.UserAddDto;
import com.sms.dto.UserLoginDto;
import com.sms.entity.User;
import com.sms.mapper.UserMapper;
import com.sms.service.IUserService;
import com.sms.utils.ValidateCodePicUtils;
import com.sms.vo.CodeVo;
import com.sms.vo.UseLoginVo;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ssnow
 * @since 2024-01-14
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    RedisTemplate redisTemplate;

    @Resource
    SmsEnableConfig smsEnableConfig;

    @Resource
    UserMapper userMapper;

    @Override
    public Result<CodeVo> vcode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //定义图形验证码的长，宽和长度
        ValidateCodePicUtils.ValidateCodePic validateCodePic = ValidateCodePicUtils.create(140, 37, 4);
        String code = validateCodePic.getCode();
        String imgId = IdUtil.simpleUUID();
        log.info("生成的验证码是：{}", code);
        //输出流，将验证码写回浏览器
        ServletOutputStream servletOutputStream = response.getOutputStream();
        response.setContentType("image/jpeg");
        validateCodePic.write(servletOutputStream);
        //将验证码存入redis，设置有效期 5分钟
        redisTemplate.boundValueOps("code:" + imgId).set(code, 5, TimeUnit.MINUTES);
        CodeVo codeVo = new CodeVo();
        codeVo.setVerificationCode(code)
                .setImgId(imgId);
        return Result.success(codeVo);
    }

    @Override
    public Result<UseLoginVo> login(HttpServletRequest request, HttpServletResponse response, UserLoginDto userLoginDto) {
        //通过redis获取验证码的值
        if (smsEnableConfig.isVerificationCode()) {
            String redisCode = (String) redisTemplate.boundValueOps("code:" + userLoginDto.getImgId()).get();
            if (StrUtil.isEmpty(redisCode) || !redisCode.equalsIgnoreCase(userLoginDto.getVerificationCode())) {
                return Result.error("验证码错误或失效，验证码有效期为5分钟");
            }
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, userLoginDto.getAccount());
        //查询用户信息
        String md5Password = SaSecureUtil.md5(userLoginDto.getPassword());
        wrapper.eq(User::getPassword, md5Password);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            return Result.error("用户不存在");
        }
        if (user.getIsBan()) {
            return Result.error("用户被禁用，无法登录");
        }
        StpUtil.login(user.getId(), new SaLoginModel()
                .setIsLastingCookie(true)        // 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
                .setTimeout(60 * 60 * 24 * 7)    // 指定此次登录token的有效期, 单位:秒 （如未指定，自动取全局配置的 timeout 值）
        );
        UseLoginVo useLoginVo = new UseLoginVo();
        BeanUtils.copyProperties(user, useLoginVo);
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        useLoginVo.setToken(tokenInfo.getTokenValue());
        return Result.success("登录成功", useLoginVo);
    }

    @Override
    public Result<String> logout() {
        return null;
    }

    @Override
    public Result<User> selfInfo(HttpServletRequest request) {
        return null;
    }

    @Override
    public Result<String> phoneCode(String username) {
        return null;
    }

    @Override
    public Result<String> resetPassword(PwdDto pwdDto) {
        return null;
    }

    @Override
    public Result<String> pwdForget(PwdForgetDto pwdForgetDto) {
        return null;
    }

    @Override
    public Result<String> add(List<UserAddDto> userAddDtos) {
        return null;
    }

    @Override
    public Result<String> csvUserAdd(MultipartFile file) {
        return null;
    }

    @Override
    public Result<UseLoginVo> edit(User user) {
        return null;
    }

    @Override
    public Result<UseLoginVo> editInfo(User user) {
        return null;
    }
}
