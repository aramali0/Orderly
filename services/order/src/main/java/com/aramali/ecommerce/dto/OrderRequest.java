package com.aramali.ecommerce.dto;

import com.aramali.ecommerce.model.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import  java.util.List;

import java.math.BigDecimal;

public record OrderRequest(

        Integer id,
        String reference,
        @Positive(message = "Order amount must be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer id is required")
        @NotEmpty(message = "Customer should be present")
        @NotBlank(message = "Customer should not be blank")
        String customerId,
        @NotEmpty(message = "You should at least purchase one product")
        List<PurchaseRequest> products

) {


}
