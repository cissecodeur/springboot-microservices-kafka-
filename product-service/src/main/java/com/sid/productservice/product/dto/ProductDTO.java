package com.sid.productservice.product.dto;

import java.time.LocalDateTime;

public record ProductDTO(
        Long id,
        String productName,
        Double price,
        LocalDateTime createdAt,
        boolean isAvailable,
        boolean isDeleted
) {


}
