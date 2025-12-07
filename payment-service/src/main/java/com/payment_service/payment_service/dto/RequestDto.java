package com.payment_service.payment_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {

    private Integer orderId;
    private Double total;

}
