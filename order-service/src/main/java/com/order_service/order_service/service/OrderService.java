package com.order_service.order_service.service;

import com.order_service.order_service.config.PaymentClient;
import com.order_service.order_service.config.ProductClient;
import com.order_service.order_service.dto.CreateOrderDto;
import com.order_service.order_service.dto.PaymentDto;
import com.order_service.order_service.dto.PaymentResponseDto;
import com.order_service.order_service.dto.ProductResponseDto;
import com.order_service.order_service.entity.Customer;
import com.order_service.order_service.entity.Order;
import com.order_service.order_service.entity.OrderDetail;
import com.order_service.order_service.exception.OrderNotFoundException;
import com.order_service.order_service.exception.PaymentProcessingException;
import com.order_service.order_service.exception.ProductNotFoundInOrderException;
import com.order_service.order_service.repository.OrderRepository;
import com.order_service.order_service.util.enums.OrderStatus;
import com.order_service.order_service.util.enums.PaymentStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final OrderRepository orderRepository;

    public OrderService(PaymentClient paymentClient, ProductClient productClient, OrderRepository orderRepository) {
        this.paymentClient = paymentClient;
        this.productClient = productClient;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(CreateOrderDto request) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING);
        List<OrderDetail> details = new ArrayList<>();
        double total = 0;
        Customer customer = new Customer();
        customer.setName(request.getCustomer().getName());
        customer.setLastName(request.getCustomer().getLastName());
        customer.setEmail(request.getCustomer().getEmail());
        customer.setCustomerId(request.getCustomer().getCustomerId());

        for (CreateOrderDto.Item item : request.getProducts()) {
            ProductResponseDto product = productClient.getProduct(item.getProductId());

            if (product == null) {
                throw new ProductNotFoundInOrderException("Product not found: " + item.getProductId());
            }

            OrderDetail detail = new OrderDetail();

            detail.setProductId(product.getId());
            detail.setTitle(product.getTitle());
            detail.setPrice(product.getPrice());
            detail.setQuantity(item.getQuantity());
            detail.setSubtotal(product.getPrice() * item.getQuantity());
            detail.setOrder(order);

            details.add(detail);
            total += detail.getSubtotal();
        }

        order.setDetails(details);
        order.setTotal(total);
        order.setCustomer(customer);

        return orderRepository.save(order);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id: " + id + " not found or does not exist"));
    }

    @Transactional
    public Order payOrder(int id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order with id: " + id + " not found or does not exist"));

        PaymentDto paymentRequest = new PaymentDto();
        paymentRequest.setOrderId(order.getId());
        paymentRequest.setTotal(order.getTotal());

        try {
            PaymentResponseDto response = paymentClient.processPayment(paymentRequest);

            if (response.getStatus() == PaymentStatus.APPROVED) {
                order.setOrderStatus(OrderStatus.PAID);
            } else if(response.getStatus() == PaymentStatus.DENIED) {
                order.setOrderStatus(OrderStatus.REJECTED);
            } else {
                order.setOrderStatus(OrderStatus.PENDING);
            }

        } catch (Exception e) {
            throw new PaymentProcessingException("Couldn't process payment for order: " + order.getId());
        }

        return orderRepository.save(order);
    }
}
