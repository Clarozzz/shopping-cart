package com.payment_service.payment_service.controller;

import com.payment_service.payment_service.dto.PaymentResponseDto;
import com.payment_service.payment_service.dto.RequestDto;
import com.payment_service.payment_service.exception.PaymentProcessingException;
import com.payment_service.payment_service.utils.enums.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    // *** For demonstration purposes only ***
    // In a real life scenario this would be a more complex logic
    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDto> processPayment(@RequestBody RequestDto paymentReqDto) {
        if (paymentReqDto.getTotal() > 0) {

            PaymentResponseDto response = new PaymentResponseDto(
                    PaymentStatus.APPROVED,
                    paymentReqDto.getOrderId(),
                    LocalDateTime.now(),
                    paymentReqDto.getTotal(),
                    "Payment successfull for order: " + paymentReqDto.getOrderId()
            );

            return ResponseEntity.ok(response);
        } else {
            PaymentResponseDto response = new PaymentResponseDto(
                    PaymentStatus.DENIED,
                    paymentReqDto.getOrderId(),
                    LocalDateTime.now(),
                    paymentReqDto.getTotal(),
                    "Payment denied for order: " + paymentReqDto.getOrderId()
            );

            return ResponseEntity.ok(response);
        }
    }

}
