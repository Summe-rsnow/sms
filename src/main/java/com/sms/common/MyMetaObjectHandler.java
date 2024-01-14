package com.sms.common;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * 公共字段自动填充配置类
 *
 * @author sssnow
 */
@Configuration
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    public Long getLoginId() {
        return StpUtil.getLoginIdAsLong();
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        Long id = this.getLoginId();
        metaObject.setValue("createUser", id);
        metaObject.setValue("updateUser", id);
        LocalDateTime now = LocalDateTime.now();
        metaObject.setValue("createTime", now);
        metaObject.setValue("updateTime", now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateUser", this.getLoginId());
        metaObject.setValue("updateTime", LocalDateTime.now());
    }
}
