package com.loanpro.calculator.controllers;

import com.loanpro.calculator.dto.OperationRequestDTO;
import com.loanpro.calculator.entities.Operation;
import com.loanpro.calculator.services.OperationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Operation", description = "Operation APIs")
@RestController
@RequestMapping("/calculator/operation")
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Create a new operation", description = "Create a new operation")
    @PostMapping
    public ResponseEntity<Operation> createOperation(@RequestBody Operation operation) {
        Operation newOperation = operationService.createOperation(operation);
        return new ResponseEntity<>(newOperation, HttpStatus.CREATED);
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Perform an operation", description = "Perform an operation")
    @PostMapping("/perform")
    public ResponseEntity<Double> performOperation(@RequestBody OperationRequestDTO request) {
        Double result = operationService.performOperation(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Generate a random String", description = "Generate a random String")
    @PostMapping("/random-string")
    public ResponseEntity<String> generateRandomString(@RequestBody OperationRequestDTO request) {
        String randomString = operationService.generateRandomString(request);
        return new ResponseEntity<>(randomString, HttpStatus.OK);
    }
}
