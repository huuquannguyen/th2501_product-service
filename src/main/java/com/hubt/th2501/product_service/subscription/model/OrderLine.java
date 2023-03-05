package com.hubt.th2501.product_service.subscription.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderLine {

    private Long id;

    private String userId;

    private Long productId;

    private boolean inCart;

    private Integer quantity;

    private String size;

    private String color;

    private Double price;

    private String imageUrl;
}
