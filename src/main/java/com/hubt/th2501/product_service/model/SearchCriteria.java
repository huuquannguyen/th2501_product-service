package com.hubt.th2501.product_service.model;

import com.hubt.th2501.product_service.constant.SearchOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    @NotBlank(message = "Criteria key cannot be blank")
    private String key;
    @NotNull(message = "Criteria value cannot be null")
    private Object value;
    @NotNull(message = "Search operation cannot be null")
    private SearchOperation operation;
}
