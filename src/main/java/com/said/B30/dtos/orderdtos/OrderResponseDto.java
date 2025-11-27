package com.said.B30.dtos.orderdtos;

import com.said.B30.infrastructure.enums.Category;
import com.said.B30.infrastructure.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponseDto(Long id,
                               Category category,
                               String description,
                               LocalDateTime orderDate,
                               LocalDateTime deliveryDate,
                               Double establishedValue,
                               OrderStatus orderStatus,
                               String invoice) {
}
