package com.sms.service.impl;


import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sms.common.Result;
import com.sms.common.SmsConfig;
import com.sms.common.SmsEnableConfig;
import com.sms.dto.*;
import com.sms.entity.User;
import com.sms.mapper.UserMapper;
import com.sms.service.IUserService;
import com.sms.utils.SendPhoneCodeUtils;
import com.sms.utils.ValidateCodePicUtils;
import com.sms.utils.ValidateCodeUtils;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
    SmsConfig smsConfig;

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
        StpUtil.logout();
        return Result.success("退出成功");
    }

    @Override
    public Result<User> selfInfo() {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        Long loginId = (Long) tokenInfo.getLoginId();
        User user = userMapper.selectById(loginId);
        return Result.success(user);
    }

    @Override
    public Result<String> phoneCode(String account) {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getAccount, account);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            return Result.error("用户名不存在");
        }
        String phone = user.getPhone();
        if (phone == null) {
            return Result.error("当前用户名未设置手机号码，请联系管理员解决问题");
        }
        String code = ValidateCodeUtils.generateNumericCode();
        log.info("用户｛｝ 生成的手机验证码为:{}", account, code);
        if (smsEnableConfig.isPhoneCode()) {
            SendPhoneCodeUtils.sendMessage(smsConfig.getSignName(), smsConfig.getTemplateCode(), phone, code);
        }
        // 向Redis存储验证码和过期时间
        redisTemplate.boundValueOps("phoneCode:" + account).set(code, 5, TimeUnit.MINUTES);
        return Result.success("获取验证码成功");
    }

    @Override
    public Result<String> resetPassword(PwdDto pwdDto) {
        String oldPwd = pwdDto.getOldPwd();
        String newPwd = pwdDto.getNewPwd();
        oldPwd = SaSecureUtil.md5(oldPwd);
        Result<User> result = selfInfo();
        User user = result.getData();
        if (!user.getPassword().equals(oldPwd)) {
            return Result.error("原密码错误，请重试");
        }
        newPwd = SaSecureUtil.md5(newPwd);
        user.setPassword(newPwd);
        userMapper.updateById(user);
        return Result.success("修改密码成功！");
    }

    @Override
    public Result<String> pwdForget(PwdForgetDto pwdForgetDto) {
        String account = pwdForgetDto.getAccount();
        // 从Redis里获取验证码
        String phoneCode = (String) redisTemplate.boundValueOps("phoneCode:" + account).get();
        if (!pwdForgetDto.getVerificationCode().equals(phoneCode)) {
            return Result.error("验证码错误或已失效，请重试");
        }
        //根据用户名获取用户信息
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::getAccount, account);
        User user = userMapper.selectOne(wrapper);

        String md5Password = DigestUtils.md5DigestAsHex(pwdForgetDto.getNewPwd().getBytes());
        user.setPassword(md5Password);
        //让Mybatis-plus可以自己填充字段
        StpUtil.login(user.getId());
        userMapper.updateById(user);
        return Result.success("重置密码成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> add(List<UserAddDto> userAddDtos) {
        List<User> userList = new ArrayList<>();
        BeanUtils.copyProperties(userAddDtos, userList);
        userList.forEach(user -> userMapper.insert(user));
        return Result.success("添加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> csvUserAdd(MultipartFile file) throws IOException {
        if (file == null) {
            return Result.error("未选择文件");
        }
        // 获取文件后缀名
        String filename = file.getOriginalFilename();
        System.out.println(filename);
        String[] split = filename.split("\\.");
        String fileExtension = split[split.length - 1];
        if (!"csv".equals(fileExtension)) {
            return Result.error("请上传格式正确的文件");
        }
        //从流读取csv文件
        InputStream in = file.getInputStream();
        BufferedReader utf8Reader = IoUtil.getUtf8Reader(in);
        CsvReader reader = CsvUtil.getReader();
        List<User> list = reader.read(utf8Reader, User.class);
        list.forEach(user -> userMapper.insert(user));
        return Result.success();
    }

    @Override
    public Result<UseLoginVo> edit(UserEditDto userEditDto) {
        User user = new User();
        BeanUtils.copyProperties(userEditDto, user);
        userMapper.updateById(user);
        user = userMapper.selectById(user.getId());
        UseLoginVo useLoginVo = new UseLoginVo();
        BeanUtils.copyProperties(user, useLoginVo);
        return Result.success(useLoginVo);
    }

    @Override
    public Result<UseLoginVo> editInfo(UserEditDto userEditDto) {
        if (StpUtil.getLoginIdAsLong() != userEditDto.getId()) {
            return Result.error("不能修改别人的信息");
        }
        User user = new User();
        BeanUtils.copyProperties(userEditDto, user);
        userMapper.updateById(user);
        user = userMapper.selectById(user.getId());
        UseLoginVo useLoginVo = new UseLoginVo();
        BeanUtils.copyProperties(user, useLoginVo);
        return Result.success(useLoginVo);
    }

    @Override
    public Result<String> ban(Long id, Integer ban) {
        User user = new User();
        boolean b = false;
        if (ban == 0) b = false;
        if (ban == 1) b = true;
        user.setIsBan(b).setId(id);
        userMapper.updateById(user);
        return Result.success();
    }
}
