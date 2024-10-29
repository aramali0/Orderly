package com.aramali.ecommerce.notification;

import com.aramali.ecommerce.model.PaymentMethode;

import java.math.BigDecimal;

public record PaymentNotificationRequest (

        String orderReference,
        BigDecimal amount,
        PaymentMethode paymentMethode,
        String customerFirstName,
        String customerLastName,
        String customerEmail
){
}
