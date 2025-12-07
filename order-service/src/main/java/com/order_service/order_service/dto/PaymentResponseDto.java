package com.order_service.order_service.dto;

import com.order_service.order_service.util.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PaymentResponseDto {

    private PaymentStatus status;
    private Integer id;
    private LocalDateTime dateTime;
    private Double amount;
    private String message;

}
