package com.order_service.order_service.config;

import com.order_service.order_service.dto.PaymentDto;
import com.order_service.order_service.dto.PaymentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "payment-service", url = "${payment.service.url}")
public interface PaymentClient {

    @PostMapping("/process")
    PaymentResponseDto processPayment(@RequestBody PaymentDto paymentRequest);

}
