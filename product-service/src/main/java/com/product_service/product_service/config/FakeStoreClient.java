package com.product_service.product_service.config;

import com.product_service.product_service.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "fake-store", url = "https://fakestoreapi.com")
public interface FakeStoreClient {

    @GetMapping("/products")
    List<ProductDto> getProducts();

    @GetMapping("/products/{id}")
    ProductDto getProductById(@PathVariable("id") int id);

}
