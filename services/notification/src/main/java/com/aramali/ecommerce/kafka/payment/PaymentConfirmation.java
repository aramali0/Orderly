package com.aramali.ecommerce.kafka.payment;

import com.aramali.ecommerce.model.PaymentMethod;

import java.math.BigDecimal;

public record PaymentConfirmation(

        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstname,
        String customerLastname,
        String email

) {
}
