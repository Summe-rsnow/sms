package com.sms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author ssnow
 * @since 2024-01-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sms_user")
@ApiModel(value = "User对象", description = "")
public class User extends BaseEntity{
    @TableField("account")
    private String account;

    @TableField("password")
    private String password;

    @TableField("name")
    private String name;

    @TableField("email")
    private String email;

    @TableField("phone")
    private String phone;

    @TableField("id_card")
    private String idCard;

    @TableField("location")
    private String location;

    @TableField("gender")
    private Integer gender;

    @TableField("age")
    private Integer age;

    @TableField("role")
    private Integer role;

    @TableField("avatar")
    private String avatar;

    @TableField("is_ban")
    private Boolean isBan;
}
