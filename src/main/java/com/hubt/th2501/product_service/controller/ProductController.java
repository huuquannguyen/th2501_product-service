package com.hubt.th2501.product_service.controller;

import com.hubt.th2501.product_service.entity.Product;
import com.hubt.th2501.product_service.model.request.CreateProductRequest;
import com.hubt.th2501.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product/create")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest createProductRequest){
        return ResponseEntity.ok().body(productService.createProduct(createProductRequest));
    }

}
