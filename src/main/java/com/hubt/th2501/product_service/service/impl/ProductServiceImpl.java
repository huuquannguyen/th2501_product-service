package com.hubt.th2501.product_service.service.impl;

import com.hubt.th2501.product_service.entity.Product;
import com.hubt.th2501.product_service.entity.Size;
import com.hubt.th2501.product_service.model.request.CreateProductRequest;
import com.hubt.th2501.product_service.model.request.SizeRequest;
import com.hubt.th2501.product_service.repository.ProductRepository;
import com.hubt.th2501.product_service.service.ProductService;
import com.hubt.th2501.product_service.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Product createProduct(CreateProductRequest request) throws IOException {
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
            size.setStatus(sizeRequest.getStatus().getStatus());
            size.setProduct(product);
            product.getSizes().add(size);
        }
        productRepository.save(product);
        String imageName = FileUploadUtil.saveFile(product.getId(), request.getImage());
        product.setImageName(imageName);
        productRepository.save(product);
        return product;
    }
}
