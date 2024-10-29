package com.aramali.ecommerce.service;

import com.aramali.ecommerce.customer.Customer;
import com.aramali.ecommerce.exception.CustomerNotFoundException;
import com.aramali.ecommerce.mapper.CustomerMapper;
import com.aramali.ecommerce.mapper.CustomerRequest;
import com.aramali.ecommerce.mapper.CustomerResponse;
import com.aramali.ecommerce.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    public final CustomerRepository customerRepository;
    public final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest customerRequest) {
        var customer = customerRepository.save(customerMapper.toCustomer(customerRequest));
        return customer.getId();
    }


    public void updateCustomer(CustomerRequest request) throws CustomerNotFoundException {
       var customer = customerRepository.findById(request.id()).orElseThrow(
               () -> new CustomerNotFoundException(String.format("Customer with id %s not found", request.id())));

       mergerCustomer(customer, request);
         customerRepository.save(customerMapper.toCustomer(request));

    }


    private void mergerCustomer(Customer customer, CustomerRequest request) {

        if(!StringUtils.isEmpty(request.firstName())) {
            customer.setFirstName(request.firstName());
        }
        if(!StringUtils.isEmpty(request.lastName())) {
            customer.setLastName(request.lastName());
        }
        if(!StringUtils.isEmpty(request.email())) {
            customer.setEmail(request.email());
        }
        if(request.address() != null) {
            customer.setAddress(request.address());
        }
    }


    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customerMapper::toCustomerResponse)
                .toList();
    }

    public Boolean customerExists(String customerId) {
        return customerRepository.findById(customerId).isPresent();
    }

    public CustomerResponse findById(String customerId) throws CustomerNotFoundException {
        return  customerRepository.findById(customerId)
                .map(customerMapper::toCustomerResponse)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with id %s not found", customerId)));
    }

    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
