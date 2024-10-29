package com.aramali.ecommerce.dto;

import com.aramali.ecommerce.model.PaymentMethode;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        PaymentMethode paymentMethode,
        Integer orderId,
        String orderReference,
        Customer customer
) {
}
