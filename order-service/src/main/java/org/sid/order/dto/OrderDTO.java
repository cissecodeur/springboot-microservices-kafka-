package org.sid.order.dto;

public record OrderDTO(
        Long id,
        String orderId,
        String orderName,
        int qty,
        double price

){




}
