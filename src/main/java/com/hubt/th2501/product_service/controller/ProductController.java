package com.hubt.th2501.product_service.controller;

import com.hubt.th2501.product_service.entity.Product;
import com.hubt.th2501.product_service.controller.request.CreateProductRequest;
import com.hubt.th2501.product_service.exception.ApiException;
import com.hubt.th2501.product_service.model.ApiResponse;
import com.hubt.th2501.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(path = "/products", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Product> createProduct(@ModelAttribute CreateProductRequest createProductRequest) throws IOException, ApiException {
        return ApiResponse.successWithResult(productService.createProduct(createProductRequest));
    }

    @GetMapping("/products")
    public ApiResponse<List<Product>> getProducts(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false) String sort,
                                                  @RequestParam(required = false, defaultValue = "20") Integer limit){
        return ApiResponse.successWithResult(productService.getAllProducts(page, sort, limit).getContent());
    }

}
