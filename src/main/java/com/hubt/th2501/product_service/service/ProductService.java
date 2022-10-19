package com.hubt.th2501.product_service.service;

import com.hubt.th2501.product_service.entity.Product;
import com.hubt.th2501.product_service.controller.request.CreateProductRequest;
import com.hubt.th2501.product_service.exception.ApiException;

import java.io.IOException;

public interface ProductService {
    Product createProduct(CreateProductRequest request) throws ApiException, IOException;
}
