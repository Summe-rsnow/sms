package com.sms.dto;

import lombok.Data;

/**
 * @author sssnow
 */
@Data
public class UserLoginDto {
    private String account;
    private String password;
    private String verificationCode;
    private String imgId;
    private boolean rememberMe;
}
