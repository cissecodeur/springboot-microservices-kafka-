package org.sid.basedomains.dto;

public record OrderDTO (
        Long id,
        String orderId,
        String orderName,
        int qty,
        double price

){




}
