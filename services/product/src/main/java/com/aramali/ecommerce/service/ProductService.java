package com.aramali.ecommerce.service;

import com.aramali.ecommerce.dto.ProductPurchaseRequest;
import com.aramali.ecommerce.dto.ProductPurchaseResponse;
import com.aramali.ecommerce.dto.ProductRequest;
import com.aramali.ecommerce.dto.ProductResponse;
import com.aramali.ecommerce.exception.ProductPurchaseException;
import com.aramali.ecommerce.mapper.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createProduct(ProductRequest request) {
        var product = productMapper.toProduct(request);
        return productRepository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest> request) {
        var producIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        var storedProducts = productRepository.findAllByIdInOrderById(producIds);
        if(storedProducts.size() != producIds.size()){
            throw new ProductPurchaseException("One or more products not found");
        }

        var storesRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .collect(Collectors.toList());

        var purchaseProducts = new ArrayList<ProductPurchaseResponse>();

        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var purchaseRequest = storesRequest.get(i);

            if(product.getAvailableQuantity() < purchaseRequest.quantity()){
                throw new ProductPurchaseException("Product not available in stock");
            }

            var newAvailableQuantity = product.getAvailableQuantity() - purchaseRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);

            purchaseProducts.add(productMapper.toProductPurchaseResponse(product,purchaseRequest.quantity()));



        }
    return purchaseProducts;
    }

    public ProductResponse findById(Integer productId) {

        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id:: " + productId));
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
