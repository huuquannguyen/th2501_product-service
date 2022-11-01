package com.hubt.th2501.product_service.controller.request;

import com.hubt.th2501.product_service.constant.Category;
import com.hubt.th2501.product_service.constant.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

    @NotBlank(message = "Product name must not be blank.")
    private String name;

    @NotNull(message = "Product category must not be blank.")
    private Category category;

    @NotNull(message = "Product subject must not be blank.")
    private Subject subject;

    @NotNull(message = "Product price must not be null.")
    private Double price;

    @Valid
    private List<SizeRequest> sizes;

    private String description;

    private MultipartFile image;
}
