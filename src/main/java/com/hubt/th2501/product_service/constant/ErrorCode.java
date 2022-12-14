package com.hubt.th2501.product_service.constant;

import lombok.Getter;

@Getter
public enum ErrorCode {

    UNKNOWN_ERROR("UNKNOWN_ERROR", "Unknown error"),

    FILE_NOT_EXIST("FILE_NOT_EXIST", "File doesn't exist"),

    UPLOAD_IMAGE_ERROR("UPLOAD_FILE_ERROR", "Error when upload file"),

    PRODUCT_SIZE_NOT_ENOUGH("PRODUCT_SIZE_NOT_ENOUGH", "Doesn't have enought size for this product"),

    PRODUCT_SIZE_DOESNT_EXIST("SIZE_DOESNT_EXIST", "Product's size doesn't exist"),

    SIZE_REQUEST_NOT_VALID("SIZE_REQUEST_NOT_VALID", "Size request should have one and only size type of character or number"),

    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND", "Can not found this product");

    private final String code;
    private final String message;


    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
