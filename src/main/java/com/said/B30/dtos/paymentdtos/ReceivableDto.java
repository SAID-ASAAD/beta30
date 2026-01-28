package com.said.B30.dtos.paymentdtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record ReceivableDto(
    Long id,
    String type,
    String description,
    String clientName,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    LocalDate dueDate,
    Double totalValue,
    Double paidValue,
    Double remainingValue
) {}
