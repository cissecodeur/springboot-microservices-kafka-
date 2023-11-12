package org.sid.stock.dto;

public record OrderDTO(
        Long id,
        String orderId,
        String orderName,
        int qty,
        double price

){




}
