package com.quickcommerce.thiskostha.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private String status;
    private String method;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Payment() {}

    public Payment(Long paymentId, String status, String method, Order order) {
        this.paymentId = paymentId;
        this.status = status;
        this.method = method;
        this.order = order;
    }

    // Getters & Setters
    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}