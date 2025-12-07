package com.payment_service.payment_service.dto;

import com.payment_service.payment_service.utils.enums.PaymentStatus;
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
