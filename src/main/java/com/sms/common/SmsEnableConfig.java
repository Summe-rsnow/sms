package com.sms.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sms.enable")
public class SmsEnableConfig {
    private boolean phoneCode;
    private boolean verificationCode;
}
