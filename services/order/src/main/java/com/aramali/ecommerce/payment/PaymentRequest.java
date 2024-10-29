package com.aramali.ecommerce.payment;

import com.aramali.ecommerce.model.PaymentMethod;
import com.aramali.ecommerce.service.CustomerResponse;

import java.math.BigDecimal;

public record PaymentRequest(

        BigDecimal amount,
        PaymentMethod paymentMethode,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {

}
