package com.hubt.th2501.product_service.controller.response;

import com.hubt.th2501.product_service.controller.request.MoveToStoreRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveFailedResponse {
    private MoveToStoreRequest request;
    private List<String> failureReasons = new ArrayList<>();
}
