package com.order_service.order_service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class Customer {

    // Embeddable for the sake of demonstration

    private Integer customerId;
    private String name;
    private String lastName;
    private String email;

}
