package com.hubt.th2501.product_service.controller.response;

import com.hubt.th2501.product_service.controller.request.MoveToStoreRequest;
import com.hubt.th2501.product_service.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveToStoreResponse {
    private List<MoveToStoreRequest> moveSuccess = new ArrayList<>();
    private List<MoveFailedResponse> moveFailed = new ArrayList<>();
}
