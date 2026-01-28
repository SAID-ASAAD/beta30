package com.said.B30.dtos.paymentdtos;

public record ProfitDetailDto(
    Long id,
    String type,
    String description,
    Double revenue,
    Double cost,
    Double profit
) {}
