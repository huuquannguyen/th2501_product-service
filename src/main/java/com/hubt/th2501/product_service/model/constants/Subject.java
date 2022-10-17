package com.hubt.th2501.product_service.model.constants;

import lombok.Getter;

@Getter
public enum Subject {

    MALE("male"),
    FEMALE("female"),
    KID("kids");

    private final String subject;

    Subject(String subject){
        this.subject = subject;
    }
}
