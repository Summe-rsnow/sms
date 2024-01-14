package com.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sms.common.Result;
import com.sms.dto.PwdDto;
import com.sms.dto.PwdForgetDto;
import com.sms.dto.UserAddDto;
import com.sms.dto.UserLoginDto;
import com.sms.entity.User;
import com.sms.vo.CodeVo;
import com.sms.vo.UseLoginVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ssnow
 * @since 2024-01-14
 */
public interface IUserService extends IService<User> {

    Result<CodeVo> vcode(HttpServletRequest request, HttpServletResponse response) throws IOException;

    Result<UseLoginVo> login(HttpServletRequest request, HttpServletResponse response, UserLoginDto userLoginDto);

    Result<String> logout();

    Result<User> selfInfo(HttpServletRequest request);

    Result<String> phoneCode(String username);

    Result<String> resetPassword(PwdDto pwdDto);

    Result<String> pwdForget(PwdForgetDto pwdForgetDto);

    Result<String> add(List<UserAddDto> userAddDtos);

    Result<String> csvUserAdd(MultipartFile file);

    Result<UseLoginVo> edit(User user);

    Result<UseLoginVo> editInfo(User user);
}
