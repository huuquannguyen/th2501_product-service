package com.hubt.th2501.product_service.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductInStoreResponse {
    private Long productId;
    private String name;
    private String category;
    private String subject;
    private double price;
    private int sold;
    private String description;
    private String imageName;
    private String size;
    private int quantity;
    private int inStore;
}
