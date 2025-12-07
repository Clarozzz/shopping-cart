package com.product_service.product_service.controller;

import com.product_service.product_service.config.FakeStoreClient;
import com.product_service.product_service.dto.ProductDto;
import com.product_service.product_service.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final FakeStoreClient fakeStoreClient;

    public ProductController(FakeStoreClient fakeStoreClient) {
        this.fakeStoreClient = fakeStoreClient;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(fakeStoreClient.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") int id) {
        ProductDto product = fakeStoreClient.getProductById(id);

        if (product == null) {
            throw new ProductNotFoundException("Product with id: " + id + " not found");
        }

        return ResponseEntity.ok(product);
    }

}
