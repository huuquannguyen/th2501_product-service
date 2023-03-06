package com.hubt.th2501.product_service.subscription;

import com.hubt.th2501.product_service.subscription.model.OrderProductEvent;
import com.hubt.th2501.product_service.subscription.service.OrderEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Configuration
public class ProductEventListener {

    private final OrderEventService orderEventService;

    @Bean
    public Consumer<OrderProductEvent> orderEventHandler() {
        return orderEventService::orderEventHandler;
    }
}
