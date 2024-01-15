package com.sms.dto;

import com.sms.validation.Gender;
import com.sms.validation.IdCard;
import com.sms.validation.Phone;
import com.sms.validation.Role;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * @author sssnow
 */
@Data
public class UserAddDto {
    @NotNull
    private String account;//账号
    private String name;//真实姓名
    @Email
    private String email;//邮箱
    @Gender
    private Integer gender;//性别
    private String location;//籍贯
    @DecimalMin(value = "0",message = "年龄不能小于0岁")
    @DecimalMax(value = "120",message = "年龄不能大于120岁")
    private Integer age;//年龄
    @Phone
    private String phone;//手机号码
    @IdCard
    private String idCard;//身份证号码
    @Role
    private Integer role;//权限等级 0管理员 1教师 2学生
}
