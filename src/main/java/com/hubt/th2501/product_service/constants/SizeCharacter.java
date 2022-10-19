package com.hubt.th2501.product_service.constants;

import lombok.Getter;

@Getter
public enum SizeCharacter {

    SIZE_SMALL("S"),
    SIZE_MEDIUM("M"),
    SIZE_LARGE("L"),
    SIZE_X_LARGE("XL"),
    SIZE_XX_LARGE("XXL");

    private final String code;

    SizeCharacter(String code){
        this.code = code;
    }
}
