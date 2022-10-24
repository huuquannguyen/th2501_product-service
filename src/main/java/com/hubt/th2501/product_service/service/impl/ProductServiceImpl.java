package com.hubt.th2501.product_service.service.impl;

import com.hubt.th2501.product_service.controller.request.MoveToStoreRequest;
import com.hubt.th2501.product_service.controller.response.MoveFailedResponse;
import com.hubt.th2501.product_service.controller.response.MoveToStoreResponse;
import com.hubt.th2501.product_service.entity.Product;
import com.hubt.th2501.product_service.entity.Size;
import com.hubt.th2501.product_service.controller.request.CreateProductRequest;
import com.hubt.th2501.product_service.controller.request.SizeRequest;
import com.hubt.th2501.product_service.exception.ApiException;
import com.hubt.th2501.product_service.repository.ProductRepository;
import com.hubt.th2501.product_service.service.ProductService;
import com.hubt.th2501.product_service.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product createProduct(CreateProductRequest request) throws ApiException, IOException {
        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory().getCategory());
        product.setPrice(request.getPrice());
        product.setSubject(request.getSubject().getSubject());
        product.setDescription(request.getDescription());
        for (SizeRequest sizeRequest : request.getSizes()){
            Size size = new Size();
            size.setSize(sizeRequest.getSizeNumber() == null ? sizeRequest.getSizeCharacter().getCode() :
                    String.valueOf(sizeRequest.getSizeNumber()));
            size.setQuantity(sizeRequest.getQuantity());
            size.setProduct(product);
            product.getSizes().add(size);
        }
        productRepository.save(product);
        if(request.getImage() != null){
            String imageName = FileUploadUtil.uploadImage(product.getId(), request.getImage());
            product.setImageName(imageName);
            productRepository.save(product);
        }
        return product;
    }

    @Override
    public Page<Product> getAllProducts(Integer page, String order, Integer limit) {
        Pageable pageable = pagination(page, order, limit);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> getAllInStoreProducts(Integer page, String sort, Integer limit) {
        Pageable pageable = pagination(page, sort, limit);
        return productRepository.findInStoreProducts(pageable);
    }

    @Override
    public MoveToStoreResponse moveProductToStore(List<MoveToStoreRequest> requests) {
        MoveToStoreResponse response = new MoveToStoreResponse();
        for (MoveToStoreRequest request: requests) {
            Optional<Product> optional = productRepository.findById(request.getProductId());
            if(optional.isPresent()){
                Product product = optional.get();
                updateSizeMove(product, request, response);
                productRepository.save(product);
            } else {
                response.getMoveFailed().add(new MoveFailedResponse(request, List.of("Product's size does not exist")));
            }
        }
        return response;
    }

    private Pageable pagination(Integer page, String order, Integer limit){
        if(order == null){
            return PageRequest.of(page, limit);
        }else{
            return PageRequest.of(page, limit, Sort.by(order));
        }
    }

    private void updateSizeMove(Product product, MoveToStoreRequest request, MoveToStoreResponse response){
        List<String> failureReasons = new ArrayList<>();
        MoveToStoreRequest movedSuccessProduct = new MoveToStoreRequest();
        movedSuccessProduct.setProductId(request.getProductId());
        movedSuccessProduct.setSizes(new ArrayList<>());
        for (SizeRequest sizeRequest : request.getSizes()){
            String requestSize = sizeRequest.getSizeNumber() == null ? sizeRequest.getSizeCharacter().getCode() : String.valueOf(sizeRequest.getSizeNumber());
            Optional<Size> optional = product.getSizes().stream().filter(s -> s.getSize().equals(requestSize)).findFirst();
            if(optional.isPresent()){
                Size size = optional.get();
                if(size.getQuantity() < sizeRequest.getQuantity()){
                    failureReasons.add("Size " + requestSize + " does not have enough quantity.");
                } else {
                    size.setInStore(size.getInStore() + sizeRequest.getQuantity());
                    movedSuccessProduct.getSizes().add(sizeRequest);
                }
            } else {
                failureReasons.add("Size " + requestSize + " does not exist.");
            }
        }
        if(movedSuccessProduct.getSizes().size() > 0){
            response.getMoveSuccess().add(movedSuccessProduct);
            for (SizeRequest sizeRequest: movedSuccessProduct.getSizes()) {
                request.getSizes().remove(sizeRequest);
            }
        }
        if(failureReasons.size() > 0){
            response.getMoveFailed().add(new MoveFailedResponse(request, failureReasons));
        }
    }
}
