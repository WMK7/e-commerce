package com.example.ecommerce.dto;

import com.example.ecommerce.entities.Order;
import com.example.ecommerce.entities.OrderItem;
import com.example.ecommerce.entities.OrderItemPK;
import com.example.ecommerce.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class OrderItemDTO {
    private OrderItemPK id;
    @Positive(message = "O preço deve ser positivo")
    @NotBlank(message = "Campo requerido")
    private Integer quantity;
    @Positive(message = "O preço deve ser positivo")
    @NotBlank(message = "Campo requerido")
    private Double price;
    private Order order;
    private Product product;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Order order, Product product, Integer quantity, Double price) {
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
        this.price = price;
    }

    public OrderItemDTO(OrderItem entity){
        id = entity.getId();
        quantity = entity.getQuantity();
        price = entity.getPrice();
        order = entity.getOrder();
        product = entity.getProduct();
    }

    public OrderItemPK getId() {
        return id;
    }

    public void setId(OrderItemPK id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
