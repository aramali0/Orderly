package com.aramali.ecommerce.kafka;

import com.aramali.ecommerce.dto.PurchaseResponse;
import com.aramali.ecommerce.model.PaymentMethod;
import com.aramali.ecommerce.service.CustomerResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customerResponse,
        List<PurchaseResponse> purchaseResponses

) {
}
