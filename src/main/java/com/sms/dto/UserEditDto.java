package com.sms.dto;

import com.sms.validation.Gender;
import com.sms.validation.IdCard;
import com.sms.validation.Phone;
import com.sms.validation.Role;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserEditDto {
    @NotNull
    private Long id;

    private String name;

    @Email
    private String email;

    @Phone
    private String phone;

    @IdCard
    private String idCard;

    private String location;

    @Gender
    private Integer gender;

    @DecimalMin(value = "0", message = "年龄不能小于0岁")
    @DecimalMax(value = "120", message = "年龄不能大于120岁")
    private Integer age;

    @Role
    private Integer role;
}
