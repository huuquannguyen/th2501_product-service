package com.hubt.th2501.product_service.service.impl;

import com.hubt.th2501.product_service.constant.ErrorCode;
import com.hubt.th2501.product_service.controller.request.CreateProductRequest;
import com.hubt.th2501.product_service.controller.request.MoveToStoreRequest;
import com.hubt.th2501.product_service.controller.request.SizeRequest;
import com.hubt.th2501.product_service.controller.request.UpdateProductRequest;
import com.hubt.th2501.product_service.controller.response.MoveFailedResponse;
import com.hubt.th2501.product_service.controller.response.MoveToStoreResponse;
import com.hubt.th2501.product_service.entity.Product;
import com.hubt.th2501.product_service.entity.Size;
import com.hubt.th2501.product_service.exception.ApiException;
import com.hubt.th2501.product_service.model.ProductSpecification;
import com.hubt.th2501.product_service.model.SearchCriteria;
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
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        List<Size> sizes = getSizesFromRequest(request.getSizes());
        for(Size size : sizes){
            size.setProduct(product);
        }
        product.setSizes(sizes);
        productRepository.save(product);
        if(request.getImage() != null){
            String imageName = FileUploadUtil.uploadImage(product.getId(), request.getImage());
            product.setImageUrl(imageName);
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

    @Override
    public Product updateProduct(Long id, UpdateProductRequest request) throws ApiException, IOException {
        Optional<Product> optional = productRepository.findById(id);

        if (optional.isEmpty()) {
            throw new ApiException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        Product product = optional.get();

        if (!Objects.isNull(request.getCategory())) {
            product.setCategory(request.getCategory().getCategory());
        }

        if (request.getName() != null && !request.getName().isEmpty()) {
            product.setName(request.getName());
        }

        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }

        if (!Objects.isNull(request.getSubject())) {
            product.setSubject(request.getSubject().getSubject());
        }

        if (request.getDescription() != null && !request.getDescription().isEmpty()) {
            product.setDescription(request.getDescription());
        }

        if (request.getSizes() != null && !request.getSizes().isEmpty()) {
            List<Size> requestSizes = getSizesFromRequest(request.getSizes());
            for (Size requestSize : requestSizes) {
                for (Size size : product.getSizes()) {
                    if (size.getSize().equals(requestSize.getSize())) {
                        size.setQuantity(requestSize.getQuantity());
                        size.setInStore(requestSize.getInStore());
                    } else {
                        Size newSize = new Size();
                        newSize.setProduct(product);
                        newSize.setSize(requestSize.getSize());
                        newSize.setQuantity(requestSize.getQuantity());
                        newSize.setInStore(requestSize.getInStore());
                        product.getSizes().add(newSize);
                    }
                }
            }
        }

        if (request.getSold() != null) {
            product.setSold(request.getSold());
        }

        if (request.getImage() != null) {
            FileUploadUtil.uploadImage(id, request.getImage());
        }

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Long deleteProduct(Long id) throws ApiException {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ApiException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        Product product = optional.get();
        if (product.getImageUrl() != null) {
            deleteProductImage(product.getImageUrl());
        }
        productRepository.delete(product);
        return id;
    }

    @Override
    public Page<Product> searchProduct(Integer page, String sort, Integer limit, List<SearchCriteria> criteriaList) {
        Pageable pageable = pagination(page, sort, limit);
        ProductSpecification specification = new ProductSpecification(criteriaList);
        return productRepository.findAll(specification, pageable);
    }

    private Pageable pagination(Integer page, String order, Integer limit) {
        if (order == null) {
            return PageRequest.of(page, limit);
        } else {
            return PageRequest.of(page, limit, Sort.by(order));
        }
    }

    private void updateSizeMove(Product product, MoveToStoreRequest request, MoveToStoreResponse response) {
        List<String> failureReasons = new ArrayList<>();
        MoveToStoreRequest movedSuccessProduct = new MoveToStoreRequest();
        movedSuccessProduct.setProductId(request.getProductId());
        movedSuccessProduct.setSizes(new ArrayList<>());
        for (SizeRequest sizeRequest : request.getSizes()) {
            String requestSize = sizeRequest.getSizeNumber() == null ? sizeRequest.getSizeCharacter().getCode() : String.valueOf(sizeRequest.getSizeNumber());
            Optional<Size> optional = product.getSizes().stream().filter(s -> s.getSize().equals(requestSize)).findFirst();
            if (optional.isPresent()) {
                Size size = optional.get();
                if (size.getQuantity() < sizeRequest.getQuantity()) {
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

    private List<Size> getSizesFromRequest(List<SizeRequest> sizeRequests) {
        List<Size> sizes = new ArrayList<>();
        for (SizeRequest sizeRequest : sizeRequests) {
            Size size = new Size();
            size.setQuantity(sizeRequest.getQuantity());
            size.setSize((Objects.isNull(sizeRequest.getSizeCharacter()) ? String.valueOf(sizeRequest.getSizeNumber()) :
                    sizeRequest.getSizeCharacter().getCode()));
            sizes.add(size);
        }
        return sizes;
    }

    private void deleteProductImage(String url) throws ApiException {
        Path path = Paths.get(url);
        try {
            Files.delete(path);
        } catch (NoSuchFileException e) {
            throw new ApiException(ErrorCode.FILE_NOT_EXIST);
        } catch (IOException e) {
            throw new ApiException(ErrorCode.UNKNOWN_ERROR);
        }
    }
}
