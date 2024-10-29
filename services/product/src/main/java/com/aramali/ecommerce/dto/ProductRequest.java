package com.aramali.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;

public record ProductRequest(
        Integer id,
        @NotNull(message = "Name is required")
        String name,
        @NotNull(message = "Description is required")
        String description,
        @Positive(message = "Available Quantity must be positive")
        Double availableQuantity,
        @Positive(message = "Price must be positive")
        BigDecimal price,
        @NotNull(message = "Category Id is required")
        Integer categoryId
) {
}
