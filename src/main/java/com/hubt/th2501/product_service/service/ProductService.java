package com.hubt.th2501.product_service.service;

import com.hubt.th2501.product_service.entity.Product;
import com.hubt.th2501.product_service.model.request.CreateProductRequest;

public interface ProductService {
    Product createProduct(CreateProductRequest request);
}
