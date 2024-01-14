package com.sms.dto;

import lombok.Data;

/**
 * @author sssnow
 */
@Data
public class ChangePwdDto {
    private String oldPwd;
    private String newPwd;
}
