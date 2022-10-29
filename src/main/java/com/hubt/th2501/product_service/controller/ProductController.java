package com.hubt.th2501.product_service.controller;

import com.hubt.th2501.product_service.controller.request.MoveToStoreRequest;
import com.hubt.th2501.product_service.controller.response.MoveToStoreResponse;
import com.hubt.th2501.product_service.entity.Product;
import com.hubt.th2501.product_service.controller.request.CreateProductRequest;
import com.hubt.th2501.product_service.exception.ApiException;
import com.hubt.th2501.product_service.model.ApiResponse;
import com.hubt.th2501.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(path = "/products", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Product> createProduct(@Valid @ModelAttribute CreateProductRequest createProductRequest) throws IOException, ApiException {
        return ApiResponse.successWithResult(productService.createProduct(createProductRequest));
    }

    @GetMapping("/products")
    public ApiResponse<List<Product>> getProducts(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                  @RequestParam(required = false) String sort,
                                                  @RequestParam(required = false, defaultValue = "20") Integer limit){
        return ApiResponse.successWithResult(productService.getAllProducts(page, sort, limit).getContent());
    }

    @GetMapping("products/in_store")
    public ApiResponse<List<Product>> getInStoreProducts(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                         @RequestParam(required = false) String sort,
                                                         @RequestParam(required = false, defaultValue = "20") Integer limit){
        return ApiResponse.successWithResult(productService.getAllInStoreProducts(page, sort, limit).getContent());
    }

    @PutMapping("products/move")
    public ApiResponse<MoveToStoreResponse> moveToStore(@RequestBody List<MoveToStoreRequest> requests){
        return ApiResponse.successWithResult(productService.moveProductToStore(requests));
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<String> handleValidationExceptions(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName =((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ApiResponse.failureWithCode("BAD_REQUEST", errors.toString());
    }
}
