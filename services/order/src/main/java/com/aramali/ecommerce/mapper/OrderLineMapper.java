package com.aramali.ecommerce.mapper;

import com.aramali.ecommerce.dto.OrderLineRequest;
import com.aramali.ecommerce.dto.OrderLineResponse;
import com.aramali.ecommerce.dto.OrderResponse;
import com.aramali.ecommerce.model.Order;
import com.aramali.ecommerce.model.OrderLine;
import com.aramali.ecommerce.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLine.builder()
                .id(orderLineRequest.id())
                .quantity(orderLineRequest.quantity())
                .order(
                        Order.builder()
                                .id(orderLineRequest.orderId())
                                .build()
                )
                .productId(orderLineRequest.productId())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
