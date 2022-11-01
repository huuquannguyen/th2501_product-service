package com.hubt.th2501.product_service.constant;

import lombok.Getter;

@Getter
public enum Subject {

    MALE("male"),
    FEMALE("female"),
    KID("kid");

    private final String subject;

    Subject(String subject) {
        this.subject = subject;
    }
}
