package com.hubt.th2501.product_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubt.th2501.product_service.controller.request.CreateProductRequest;
import com.hubt.th2501.product_service.controller.request.MoveToStoreRequest;
import com.hubt.th2501.product_service.controller.request.UpdateProductRequest;
import com.hubt.th2501.product_service.controller.response.MoveToStoreResponse;
import com.hubt.th2501.product_service.entity.Product;
import com.hubt.th2501.product_service.exception.ApiException;
import com.hubt.th2501.product_service.model.ApiResponse;
import com.hubt.th2501.product_service.model.SearchCriteria;
import com.hubt.th2501.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.io.IOException;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final ObjectMapper mapper;

    @PostMapping(path = "/products", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('user')")
    public ApiResponse<Product> createProduct(@Valid @ModelAttribute CreateProductRequest createProductRequest)
            throws IOException, ApiException {
        return ApiResponse.successWithResult(productService.createProduct(createProductRequest));
    }

    @GetMapping("/products/{id}")
    @PreAuthorize("hasRole('user')")
    public ApiResponse<Product> getProduct(@PathVariable Long id) throws ApiException {
        return ApiResponse.successWithResult(productService.getOneProduct(id));
    }

    @GetMapping("/products")
    @PreAuthorize("hasRole('user')")
    public ApiResponse<List<Product>> getProducts(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false) String sort,
                                                  @RequestParam(required = false, defaultValue = "20") Integer limit,
                                                  @RequestParam(required = false) String searchCriteria) throws ApiException {
        if (searchCriteria != null && !searchCriteria.isEmpty()) {
            try {
                List<SearchCriteria> criteriaList = mapper.readValue(searchCriteria, new TypeReference<>() {
                });
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                Validator validator = factory.getValidator();
                Set<ConstraintViolation<SearchCriteria>> violations = new HashSet<>();
                for (SearchCriteria criteria : criteriaList) {
                    violations.addAll(validator.validate(criteria));
                }
                if (violations.isEmpty()) {
                    return ApiResponse.successWithResult(productService.searchProduct(page, sort, limit, criteriaList).getContent());
                } else {
                    Map<String, String> errors = new HashMap<>();
                    violations.forEach(v -> errors.put(v.getPropertyPath().toString(), v.getMessageTemplate()));
                    throw new ApiException("BAD_REQUEST", errors.toString());
                }
            } catch (JsonProcessingException e) {
                throw new ApiException("BAD_REQUEST", e.getMessage());
            }
        }
        return ApiResponse.successWithResult(productService.getAllProducts(page, sort, limit).getContent());
    }

    @GetMapping("products/in_store")
    @PreAuthorize("hasRole('user')")
    public ApiResponse<List<Product>> getInStoreProducts(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                         @RequestParam(required = false) String sort,
                                                         @RequestParam(required = false, defaultValue = "20") Integer limit) {
        return ApiResponse.successWithResult(productService.getAllInStoreProducts(page, sort, limit).getContent());
    }

    @PutMapping("products/move")
    @PreAuthorize("hasRole('user')")
    public ApiResponse<MoveToStoreResponse> moveToStore(@RequestBody List<MoveToStoreRequest> requests) {
        return ApiResponse.successWithResult(productService.moveProductToStore(requests));
    }

    @PutMapping(path = "products/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('user')")
    public ApiResponse<Product> updateProduct(@PathVariable Long id,
                                              @ModelAttribute UpdateProductRequest request) throws IOException, ApiException {
        return ApiResponse.successWithResult(productService.updateProduct(id, request));
    }

    @DeleteMapping("products/{id}")
    @PreAuthorize("hasRole('user')")
    public ApiResponse<Long> deleteProduct(@PathVariable Long id) throws ApiException {
        return ApiResponse.successWithResult(productService.deleteProduct(id));
    }
}
