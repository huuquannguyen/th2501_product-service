package com.hubt.th2501.product_service.constants;

import lombok.Getter;

@Getter
public enum ErrorCode {

    UNKNOWN_ERROR("UNKNOWN_ERROR", "Lỗi chưa xác định"),

    UPLOAD_IMAGE_ERROR("UPLOAD_FILE_ERROR", "Có lỗi khi upload ảnh"),

    PRODUCT_SIZE_NOT_ENOUGH("PRODUCT_SIZE_NOT_ENOUGH", "Không đủ size cho sản phẩm"),

    PRODUCT_SIZE_DOESNT_EXIST("SIZE_DOESNT_EXIST", "Size của sản phẩm không tồn tại");

    private final String code;
    private final String message;


    ErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }
}
