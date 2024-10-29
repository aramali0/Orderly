package com.aramali.ecommerce.mapper;
import com.aramali.ecommerce.customer.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
    String id,
    @NotNull(message = "Customer First name is required")
    String firstName,
    @NotNull(message = "Customer Last name is required")
    String lastName,
    @NotNull(message = "Customer email is required")
            @Email(message = "Customer email is invalid")
    String email,
    Address address
) {}
