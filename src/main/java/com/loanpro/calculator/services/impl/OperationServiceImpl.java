package com.loanpro.calculator.services.impl;

import com.loanpro.calculator.connectors.RandomConnector;
import com.loanpro.calculator.dto.OperationRequestDTO;
import com.loanpro.calculator.entities.Operation;
import com.loanpro.calculator.entities.User;
import com.loanpro.calculator.enums.OperationType;
import com.loanpro.calculator.repositories.OperationRepository;
import com.loanpro.calculator.services.OperationService;
import com.loanpro.calculator.services.RecordService;
import com.loanpro.calculator.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final UserService userService;
    private final RecordService recordService;
    private final RandomConnector randomConnector;

    public OperationServiceImpl(OperationRepository operationRepository, UserService userService, RecordService recordService, RandomConnector randomConnector) {
        this.operationRepository = operationRepository;
        this.userService = userService;
        this.recordService = recordService;
        this.randomConnector = randomConnector;
    }

    @Override
    public Double performOperation(OperationRequestDTO request) {
        log.info("Performing operation: {}", request);

        User user = userService.getUserById(request.getUserId());
        Operation operation = getOperationByType(request.getType());

        validateOperationRequest(user, operation, request);
        Double result = 0D;

        switch (request.getType()) {
            case ADDITION:
                result = add(request.getOperands());
                break;
            case SUBSTRACTION:
                result = subtract(request.getOperands());
                break;
            case MULTIPLICATION:
                result = multiply(request.getOperands());
                break;
            case DIVISION:
                result = divide(request.getOperands());
                break;
            default:
                throw new IllegalArgumentException("Invalid operation type");
        }

        recordService.createRecord(user, operation, result, request.getOperands());

        user.setBalance(user.getBalance() - operation.getCost());
        userService.updateUser(user);

        return result;
    }

    private void validateOperationRequest(User user, Operation operation, OperationRequestDTO request) {
        if (user == null || user.isDeleted()) {
            throw new IllegalArgumentException("User not found, id: " + request.getUserId());
        }

        if (operation == null || operation.isDeleted()) {
            throw new IllegalArgumentException("Operation not found, type: " + request.getType());
        }

        if (user.getBalance() < operation.getCost()) {
            throw new IllegalArgumentException("User does not have enough balance to perform operation, user balance: " + user.getBalance() + ", operation cost: " + operation.getCost());
        }
    }


    @Override
    public Operation createOperation(Operation operation) {
        log.info("Creating operation: {}", operation);

        Operation existingOperation = getOperationByType(operation.getType());

        if (existingOperation != null) {
            throw new IllegalArgumentException("Operation already exists, type: " + operation.getType());
        }

        return operationRepository.save(operation);
    }

    @Override
    public Operation updateOperation(Operation operation) {
        return null;
    }

    @Override
    public void deleteOperation(Long id) {
        log.info("Deleting operation: {}", id);
        Operation operation = operationRepository.findById(id).orElse(null);

        if (operation != null) {
            operation.setDeleted(true);
            operationRepository.save(operation);
        }

    }

    private Double add(Double... operands) {
        return Arrays.stream(operands).reduce(0.0, Double::sum);
    }

    private Double subtract(Double... operands) {
        return Arrays.stream(operands).reduce(0.0, (a, b) -> a - b);
    }

    private Double multiply(Double... operands) {
        return Arrays.stream(operands).reduce(1.0, (a, b) -> a * b);
    }

    private Double divide(Double... operands) {
        return Arrays.stream(operands).reduce(1.0, (a, b) -> a / b);
    }

    @Override
    public Operation getOperationByType(OperationType type) {
        log.info("Getting operation by type: {}", type);
        return operationRepository.findByType(type);
    }

    @Override
    public String generateRandomString(OperationRequestDTO request) {
        log.info("Generating random string for user: {}", request.getUserId());

        User user = userService.getUserById(request.getUserId());
        Operation operation = getOperationByType(request.getType());

        validateOperationRequest(user, operation, request);

        return randomConnector.generateRandomString();
    }


}
