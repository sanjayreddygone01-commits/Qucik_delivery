package com.quickcommerce.thiskostha.dto;

public class CancelOrderDTO {
    private String orderId;
    private String phone;
    private String cancellationReason;

    public CancelOrderDTO(String orderId, String phone, String cancellationReason) {
        this.orderId = orderId;
        this.phone = phone;
        this.cancellationReason = cancellationReason;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }
}