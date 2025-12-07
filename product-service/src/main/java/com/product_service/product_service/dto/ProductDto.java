package com.product_service.product_service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ProductDto {
    private Integer id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
    private Rating rating;

    @Data
    public static class Rating {
        private Double rate;
        private Integer count;
    }
}
