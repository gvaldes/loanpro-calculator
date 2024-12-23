package com.loanpro.calculator.services;

import com.loanpro.calculator.dto.OperationRequestDTO;
import com.loanpro.calculator.entities.Operation;
import com.loanpro.calculator.enums.OperationType;

public interface OperationService {
    Double performOperation(OperationRequestDTO request);
    Operation createOperation(Operation operation);
    Operation getOperationByType(OperationType type);
    String generateRandomString(OperationRequestDTO request);
}
