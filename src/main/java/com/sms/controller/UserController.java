package com.sms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sms.common.Result;
import com.sms.dto.*;
import com.sms.entity.User;
import com.sms.service.IUserService;
import com.sms.vo.CodeVo;
import com.sms.vo.UseLoginVo;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 用户前端控制器
 * </p>
 *
 * @author ssnow
 * @since 2024-01-14
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    IUserService iUserService;

    /**
     * 获取登录验证码的接口
     *
     * @param request
     * @param response
     */
    @ApiOperation("登录验证码")
    @GetMapping("/vcode")
    public Result<CodeVo> vcode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return iUserService.vcode(request, response);
    }

    /**
     * 用户登录接口
     *
     * @param request
     * @param userLoginDto
     * @return
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<UseLoginVo> login(HttpServletRequest request, HttpServletResponse response, @RequestBody UserLoginDto userLoginDto) {
        return iUserService.login(request, response, userLoginDto);
    }

    /**
     * 用户登出接口 前后端分离项目 登出为前端清除自身token 该接口仅为占位
     *
     * @return
     */
    @ApiOperation("用户登出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return iUserService.logout();
    }

    /**
     * remember me功能配套接口 直接通过令牌获取信息
     *
     * @param request
     * @return
     */
    @ApiOperation("通过令牌获取个人信息")
    @PostMapping("/self/info")
    public Result<User> selfInfo(HttpServletRequest request) {
        return iUserService.selfInfo(request);
    }

    /**
     * 忘记密码功能手机验证码获取接口
     *
     * @param username
     * @return
     */
    @ApiOperation("手机验证码")
    @PostMapping("/phone_code/{username}")
    public Result<String> phoneCode(@PathVariable String username) {
        return iUserService.phoneCode(username);
    }

    /**
     * 修改密码接口
     *
     * @param pwdDto
     * @return
     */
    @ApiOperation("修改密码")
    @PostMapping("/pwd")
    public Result<String> pwd(@RequestBody PwdDto pwdDto) {
        return iUserService.resetPassword(pwdDto);
    }

    /**
     * 忘记密码的修改密码接口
     *
     * @param pwdForgetDto
     * @return
     */
    @ApiOperation("忘记密码重设密码")
    @PostMapping("/pwd/forget")
    public Result<String> pwdForget(@RequestBody PwdForgetDto pwdForgetDto) {
        return iUserService.pwdForget(pwdForgetDto);
    }

    /**
     * 用户添加接口
     *
     * @param userAddDtos
     * @return
     */
    @ApiOperation("用户添加")
    @PostMapping("/add")
    @CacheEvict(cacheNames = "UserVisualization", allEntries = true)
    public Result<String> add(@RequestBody List<UserAddDto> userAddDtos) {
        return iUserService.add(userAddDtos);
    }

    /**
     * 从csv批量导入用户的接口
     *
     * @param file
     * @return
     * @throws IOException
     */
    @ApiOperation("文件批量添加用户")
    @PostMapping("/csv/add")
    @CacheEvict(cacheNames = "UserVisualization", allEntries = true)
    public Result<String> csvUserAdd(MultipartFile file) throws IOException {
        return iUserService.csvUserAdd(file);
    }

    /**
     * 用户管理的信息修改接口
     *
     * @param user
     * @return
     */
    @ApiOperation("用户管理信息修改")
    @PostMapping("/edit")
    @CacheEvict(cacheNames = "UserVisualization", allEntries = true)
    public Result<UseLoginVo> edit(@RequestBody User user) {
        return iUserService.edit(user);
    }

    /**
     * 个人修改自己信息的接口
     *
     * @param user
     * @return
     */
    @ApiOperation("个人修改信息")
    @PostMapping("/self/edit")
    @CacheEvict(cacheNames = "UserVisualization", allEntries = true)
    public Result<UseLoginVo> editInfo(@RequestBody User user) {
        return iUserService.editInfo(user);
    }

    /**
     * 禁用用户接口 ban=0时为禁用 ban=1时为启用
     *
     * @param id
     * @param ban
     * @return
     */
    @ApiOperation("禁用用户")
    @PostMapping("/ban/{ban}/{id}")
    public Result<String> banUser(@PathVariable Long id, @PathVariable Integer ban) {
        return null;
    }

    /**
     * 删除用户接口
     *
     * @param id
     * @return
     */
    @ApiOperation("删除用户")
    @PostMapping("/del/{id}")
    @CacheEvict(cacheNames = "UserVisualization", allEntries = true)
    public Result<String> delUser(@PathVariable(value = "id") Long id) {
        return null;
    }

    /**
     * 用户信息分页查询接口
     *
     * @param page
     * @param pagesize
     * @param userSelectDto
     * @return
     */
    @ApiOperation("用户信息分页查询")
    @PostMapping("/{page}/{pagesize}")
    public Result<Page<UseLoginVo>> getUserPage(@PathVariable Integer page, @PathVariable Integer pagesize, @RequestBody UserSelectDto userSelectDto) {
        return null;
    }
}
