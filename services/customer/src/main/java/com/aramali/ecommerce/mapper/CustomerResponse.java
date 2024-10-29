package com.aramali.ecommerce.mapper;
import com.aramali.ecommerce.customer.Address;

public record CustomerResponse (
    String id,
    String firstName,
    String lastName,
    String email,
    Address address
) {}
