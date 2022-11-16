package com.hubt.th2501.product_service.service;

import com.hubt.th2501.product_service.controller.request.CreateProductRequest;
import com.hubt.th2501.product_service.controller.request.MoveToStoreRequest;
import com.hubt.th2501.product_service.controller.request.UpdateProductRequest;
import com.hubt.th2501.product_service.controller.response.MoveToStoreResponse;
import com.hubt.th2501.product_service.entity.Product;
import com.hubt.th2501.product_service.exception.ApiException;
import com.hubt.th2501.product_service.model.SearchCriteria;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Product createProduct(CreateProductRequest request) throws ApiException, IOException;

    Product getOneProduct(Long id) throws ApiException;

    Page<Product> getAllProducts(Integer page, String sort, Integer limit);

    Page<Product> getAllInStoreProducts(Integer page, String sort, Integer limit);

    MoveToStoreResponse moveProductToStore(List<MoveToStoreRequest> requests);

    Product updateProduct(Long id, UpdateProductRequest request) throws ApiException, IOException;

    Long deleteProduct(Long id) throws ApiException;

    Page<Product> searchProduct(Integer page, String sort, Integer limit, List<SearchCriteria> criteriaList);
}
