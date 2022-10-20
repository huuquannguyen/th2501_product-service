package com.hubt.th2501.product_service.service.impl;

import com.hubt.th2501.product_service.entity.Product;
import com.hubt.th2501.product_service.entity.Size;
import com.hubt.th2501.product_service.controller.request.CreateProductRequest;
import com.hubt.th2501.product_service.controller.request.SizeRequest;
import com.hubt.th2501.product_service.exception.ApiException;
import com.hubt.th2501.product_service.repository.ProductRepository;
import com.hubt.th2501.product_service.service.ProductService;
import com.hubt.th2501.product_service.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
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
        Pageable pageable;
        if(order == null){
            pageable = PageRequest.of(page, limit);
        }else{
            pageable = PageRequest.of(page, limit, Sort.by(order));
        }
        return productRepository.findAll(pageable);
    }
}
