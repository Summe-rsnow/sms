package com.sms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sms.common.Result;
import com.sms.dto.*;
import com.sms.entity.User;
import com.sms.vo.UseLoginVo;
import com.sms.vo.UserPageVo;
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

    void vcode(HttpServletRequest request, HttpServletResponse response, String uuid) throws IOException;

    Result<UseLoginVo> login(HttpServletRequest request, HttpServletResponse response, UserLoginDto userLoginDto);

    Result<String> logout();

    Result<User> selfInfo();

    Result<String> phoneCode(String account);

    Result<String> resetPassword(PwdDto pwdDto);

    Result<String> pwdForget(PwdForgetDto pwdForgetDto);

    Result<String> add(List<UserAddDto> userAddDtos);

    Result<String> csvUserAdd(MultipartFile file) throws IOException;

    Result<UseLoginVo> edit(UserEditDto userEditDto);

    Result<UseLoginVo> editInfo(UserEditDto userEditDto);

    Result<String> ban(Long id, Integer ban);

    Result<String> delUser(Long id);

    Result<Page<UserPageVo>> getUserPage(Integer page, Integer pagesize, UserSelectDto userSelectDto);
}
