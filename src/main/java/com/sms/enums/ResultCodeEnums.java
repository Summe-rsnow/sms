package com.sms.enums;

import lombok.Getter;

@Getter
public enum ResultCodeEnums {
    FAIL(0),
    SUCCESS(1);

    final Integer code;

    ResultCodeEnums(Integer code) {
        this.code = code;
    }

}
