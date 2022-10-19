package com.hubt.th2501.product_service.controller;

import com.hubt.th2501.product_service.entity.Product;
import com.hubt.th2501.product_service.model.request.CreateProductRequest;
import com.hubt.th2501.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(path = "/product/create", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@ModelAttribute CreateProductRequest createProductRequest,
                                                 @RequestParam MultipartFile image) throws IOException {

        return ResponseEntity.ok().body(productService.createProduct(createProductRequest));
    }

}
