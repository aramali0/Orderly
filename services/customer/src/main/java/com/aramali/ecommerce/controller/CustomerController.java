package com.aramali.ecommerce.controller;

import com.aramali.ecommerce.exception.CustomerNotFoundException;
import com.aramali.ecommerce.mapper.CustomerRequest;
import com.aramali.ecommerce.mapper.CustomerResponse;
import com.aramali.ecommerce.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.AssertFalseValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest customer) {
        return ResponseEntity.ok(customerService.createCustomer(customer));
    }


    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest request) throws CustomerNotFoundException {
        customerService.updateCustomer(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getCustomer(){
        return ResponseEntity.ok(customerService.getAllCustomers());

    }

    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> customerExists(@PathVariable("customer-id") String customerId) {
        return ResponseEntity.ok(customerService.customerExists(customerId));
    }


    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable("customer-id") String customerId) throws CustomerNotFoundException {
        return ResponseEntity.ok(customerService.findById(customerId));
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customer-id") String customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }


}
