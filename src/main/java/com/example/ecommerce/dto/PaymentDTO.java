package com.example.ecommerce.dto;

import com.example.ecommerce.entities.Order;
import com.example.ecommerce.entities.Payment;
import com.example.ecommerce.entities.Product;

import java.time.Instant;

public class PaymentDTO {
    private Long id;
    private Instant moment;
    private Order order;

    public PaymentDTO() {
    }

    public PaymentDTO(Long id, Instant moment, Order order) {
        this.id = id;
        this.moment = moment;
        this.order = order;
    }

    public PaymentDTO(Payment entity){
        id = entity.getId();
        moment = entity.getMoment();
        order = entity.getOrder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
