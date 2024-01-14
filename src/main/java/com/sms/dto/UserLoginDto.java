package com.sms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


/**
 * @author sssnow
 */
@Data
public class UserLoginDto {
    @NotNull
    private String account;
    @NotNull
    private String password;
    private String verificationCode;
    @NotNull
    private String imgId;
    @NotNull
    private boolean rememberMe;
}
