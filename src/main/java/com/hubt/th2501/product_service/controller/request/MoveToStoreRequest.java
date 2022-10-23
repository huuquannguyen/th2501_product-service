package com.hubt.th2501.product_service.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveToStoreRequest {

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @Valid
    private List<SizeRequest> sizes;
}
