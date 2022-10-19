package com.hubt.th2501.product_service.constants;

import lombok.Getter;

@Getter
public enum Category {

    PANT("pant"),
    JACKET("jacket"),
    T_SHIRT("t-shirt"),
    SHORT("short"),
    UNDERWEAR("underwear");

    private final String category;

    Category(String category){
        this.category = category;
    }
}
