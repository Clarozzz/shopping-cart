package com.order_service.order_service.dto;

import com.order_service.order_service.entity.Customer;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDto {

    @NotEmpty
    private List<Item> products;

    private Customer customer;

    @Data
    public static class Item {
        private int productId;

        private int quantity;
    }
}
