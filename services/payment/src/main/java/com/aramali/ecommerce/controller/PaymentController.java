package com.aramali.ecommerce.controller;

import com.aramali.ecommerce.dto.PaymentRequest;
import com.aramali.ecommerce.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Integer>  createPayment(@RequestBody @Valid PaymentRequest request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }


}
