package com.loanpro.calculator.dto;

import com.loanpro.calculator.enums.OperationType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OperationRequestDTO {
    private Long userId;
    private OperationType type;
    private Double[] operands;
}
