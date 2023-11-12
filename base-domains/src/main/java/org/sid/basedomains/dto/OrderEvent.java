package org.sid.basedomains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record OrderEvent(
         String message,
         String status,
         String eventType,
         OrderDTO orderDTO
) {

}
