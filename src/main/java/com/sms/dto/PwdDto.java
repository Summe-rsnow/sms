package com.sms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author sssnow
 */
@Data
public class PwdDto {
    @NotNull
    private String oldPwd;
    @NotNull
    private String newPwd;
}
