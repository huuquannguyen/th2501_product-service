package com.hubt.th2501.product_service.controller.request;

import com.hubt.th2501.product_service.constant.Category;
import com.hubt.th2501.product_service.constant.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {

    private String name;

    private Category category;

    private Subject subject;

    private Double price;

    @Valid
    private List<SizeRequest> sizes;

    private String description;

    private Integer sold;

    private MultipartFile image;
}
