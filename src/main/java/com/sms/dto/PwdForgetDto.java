package com.sms.dto;

import lombok.Data;

/**
 * @author sssnow
 */
@Data
public class PwdForgetDto {
    private String account;
    private String verificationCode;
    private String newPwd;
}
