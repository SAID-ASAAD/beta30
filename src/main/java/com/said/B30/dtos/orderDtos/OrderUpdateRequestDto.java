package com.said.B30.dtos.orderDtos;

import com.said.B30.infrastructure.enums.Category;
import com.said.B30.infrastructure.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderUpdateRequestDto(Category category,
                                    String description,
                                    LocalDateTime deliveryDate,
                                    Double establishedValue,
                                    Long clientId,
                                    String invoice,
                                    String productionProcessNote,
                                    OrderStatus orderStatus) {
}
