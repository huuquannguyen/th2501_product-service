package com.hubt.th2501.product_service.subscription;

import com.hubt.th2501.product_service.repository.SizeRepository;
import com.hubt.th2501.product_service.subscription.model.OrderLine;
import com.hubt.th2501.product_service.subscription.model.OrderProductEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventListener {

    private final SizeRepository sizeRepository;

    @StreamListener(Sink.INPUT)
    @Transactional
    public void productEventProcess(OrderProductEvent orderProductEvent) {
        switch (orderProductEvent.getEventType()) {
            case OrderProductEvent.PLACE_ORDER_EVENT:
                for (OrderLine orderLine : orderProductEvent.getOrderLines()) {
                    sizeRepository.updateQuantity(orderLine);
                    log.info("Place order with line id: {} successfully", orderLine.getId());
                }
                break;
            case OrderProductEvent.CANCEL_ORDER_EVENT:
                for (OrderLine orderLine : orderProductEvent.getOrderLines()) {
                    orderLine.setQuantity(orderLine.getQuantity() * (-1));
                    sizeRepository.updateQuantity(orderLine);
                    log.info("Cancel order with line id: {} successfully", orderLine.getId());
                }
                break;
            default:
                log.error("EventType is not correct: " + orderProductEvent.getEventType());
        }
    }
}
