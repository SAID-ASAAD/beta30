package com.said.B30.dtos.orderdtos;

import com.said.B30.infrastructure.enums.Category;

import java.time.LocalDateTime;

public record OrderRequestDto(
                              Category category,
                              String description,
                              LocalDateTime deliveryDate,
                              Double establishedValue,
                              Long clientId) {

}
