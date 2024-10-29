package com.aramali.ecommerce.service;

import com.aramali.ecommerce.dto.OrderRequest;
import com.aramali.ecommerce.dto.OrderLineRequest;
import com.aramali.ecommerce.dto.OrderResponse;
import com.aramali.ecommerce.dto.PurchaseRequest;
import com.aramali.ecommerce.exception.BusinessException;
import com.aramali.ecommerce.kafka.OrderConfirmation;
import com.aramali.ecommerce.kafka.OrderProducer;
import com.aramali.ecommerce.mapper.OrderMapper;
import com.aramali.ecommerce.payment.PaymentClient;
import com.aramali.ecommerce.payment.PaymentRequest;
import com.aramali.ecommerce.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;


    public Integer createOrder(OrderRequest orderRequest) {
        //check the customer --> OpenFeign
        var customer = this.customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Connot create order: Customer not found"));

       //purchase the products --> product-ms (RestTemplate)
         var purchaseProducts = this.productClient.purchaseProducts(orderRequest.products());

       //persist order
        var order = this.orderRepository.save(orderMapper.toOrder(orderRequest));

        for(PurchaseRequest product : orderRequest.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                    null,
                    order.getId(),
                    product.productId(),
                    product.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                orderRequest.amount(),
                orderRequest.paymentMethod(),
                order.getId(),
                orderRequest.reference(),
                customer
        );

        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchaseProducts
                )
        );

       // start payment process
    return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::fromOrder)
                .toList();
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }
}
