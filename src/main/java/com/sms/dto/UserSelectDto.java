package com.sms.dto;

import com.sms.validation.Gender;
import com.sms.validation.Role;
import lombok.Data;

/**
 * @author sssnow
 */
@Data
public class UserSelectDto {
    private String name;
    @Gender
    private Integer gender;
    @Role
    private Integer role;//权限等级 0管理员 1教师 2学生
}
