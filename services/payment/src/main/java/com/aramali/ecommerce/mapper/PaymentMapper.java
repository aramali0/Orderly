package com.aramali.ecommerce.mapper;

import com.aramali.ecommerce.dto.PaymentRequest;
import com.aramali.ecommerce.model.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(PaymentRequest request) {

        return Payment.builder()
                .id(request.id())
                .orderId(request.orderId())
                .paymentMethode(request.paymentMethode())
                .amount(request.amount())
                .build();
    }
}
