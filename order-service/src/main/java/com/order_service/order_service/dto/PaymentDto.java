package com.order_service.order_service.dto;

import lombok.Data;

@Data
public class PaymentDto {

    private Integer orderId;
    private Double total;
}
