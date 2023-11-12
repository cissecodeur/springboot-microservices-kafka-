package org.sid.order.dto;


public record OrderEvent(
         String message,
         String status,
         String eventType,
         OrderDTO orderDTO
) {

}
