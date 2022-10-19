package com.hubt.th2501.product_service.constants;

import lombok.Getter;

@Getter
public enum ProductStatus {

    IN_STORE("in store"),
    IN_STOCK("in stock");

    private final String status;

    ProductStatus(String status){
        this.status = status;
    }
}
