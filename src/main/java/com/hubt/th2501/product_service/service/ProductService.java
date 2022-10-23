package com.hubt.th2501.product_service.service;

import com.hubt.th2501.product_service.controller.request.MoveToStoreRequest;
import com.hubt.th2501.product_service.controller.response.MoveToStoreResponse;
import com.hubt.th2501.product_service.entity.Product;
import com.hubt.th2501.product_service.controller.request.CreateProductRequest;
import com.hubt.th2501.product_service.exception.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Product createProduct(CreateProductRequest request) throws ApiException, IOException;

    Page<Product> getAllProducts(Integer page, String sort, Integer limit);

    Page<Product> getAllInStoreProducts(Integer page, String sort, Integer limit);

    MoveToStoreResponse moveProductToStore(List<MoveToStoreRequest> requests);
}
