package com.sms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author sssnow
 */
@Data
public class PwdForgetDto {
    @NotNull
    private String account;
    @NotNull
    private String verificationCode;
    @NotNull
    private String newPwd;
}
