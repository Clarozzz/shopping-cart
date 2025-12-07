package com.order_service.order_service.dto;

import lombok.Data;

@Data
public class ProductResponseDto {

    private Integer id;
    private String title;
    private Double price;

}
