package com.aramali.ecommerce.dto;

import com.aramali.ecommerce.repository.OrderLineRepository;

public record OrderLineResponse (
        Integer id,
        double quantity
){ }
