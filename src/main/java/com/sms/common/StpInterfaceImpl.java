package com.sms.common;

import cn.dev33.satoken.stp.StpInterface;
import com.sms.entity.User;
import com.sms.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限加载接口实现类
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    @Resource
    IUserService iUserService;


    @Override
    public List<String> getPermissionList(Object o, String s) {
        return null;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询角色
        List<String> list = new ArrayList<>();
        User user = iUserService.getById((Long) loginId);
        list.add(user.getRole().toString());
        return list;
    }

}

