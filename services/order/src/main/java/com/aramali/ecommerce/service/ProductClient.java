package com.aramali.ecommerce.service;

import com.aramali.ecommerce.dto.PurchaseRequest;
import com.aramali.ecommerce.dto.PurchaseResponse;
import com.aramali.ecommerce.exception.BusinessException;
import jakarta.ws.rs.POST;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;


@Service
@RequiredArgsConstructor
public class ProductClient {

    @Value("${application.config.product-url}")
    public  String productUrl;

    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> purchaseRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, "application/json");

        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(purchaseRequest, headers);
        ParameterizedTypeReference<List<PurchaseResponse>> responseType = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<List<PurchaseResponse>> responseEntity = restTemplate.exchange(
                productUrl + "/purchase",
                HttpMethod.POST,
                requestEntity,
                responseType
        );

        if (responseEntity.getStatusCode().isError()) {
            throw new BusinessException("An error occurred while processing the products purchase");
        }

        return responseEntity.getBody();
    }
}
