package com.aramali.ecommerce.service;

import com.aramali.ecommerce.dto.OrderLineRequest;
import com.aramali.ecommerce.dto.OrderLineResponse;
import com.aramali.ecommerce.mapper.OrderLineMapper;
import com.aramali.ecommerce.repository.OrderLineRepository;
import com.aramali.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {
    private  final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        var order = orderLineMapper.toOrderLine(orderLineRequest);
        return orderLineRepository.save(order).getId();

    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return  orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderLineMapper::toOrderLineResponse)
                .toList();
    }
}
