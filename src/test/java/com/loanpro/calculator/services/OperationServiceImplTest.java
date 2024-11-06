package com.loanpro.calculator.services;

import com.loanpro.calculator.connectors.RandomConnector;
import com.loanpro.calculator.dto.OperationRequestDTO;
import com.loanpro.calculator.entities.Operation;
import com.loanpro.calculator.entities.User;
import com.loanpro.calculator.enums.OperationType;
import com.loanpro.calculator.repositories.OperationRepository;
import com.loanpro.calculator.services.impl.OperationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OperationServiceImplTest {

    @Mock
    private UserService userService;
    @Mock
    private RecordService recordService;
    @Mock
    private OperationRepository operationRepository;
    @Mock
    RandomConnector randomConnector;
    @Captor
    private ArgumentCaptor<User> userCaptor;

    OperationService operationService;

    @BeforeEach
    void setUp() {
        operationService = new OperationServiceImpl(operationRepository, userService, recordService, randomConnector);
    }

    @Test
    void performOperation_whenAddition_shouldStoreRecord() {

        Double[] operands = new Double[]{1D, 2D, 3D};
        // Arrange
        OperationRequestDTO request = new OperationRequestDTO();
        request.setUserId(1L);
        request.setType(OperationType.ADDITION);
        request.setNumbers(operands);

        User user = new User();
        user.setId(1L);
        user.setBalance(100D);

        Operation operation = new Operation();
        operation.setId(1L);
        operation.setCost(10D);

        when(userService.getUserById(1L)).thenReturn(user);
        when(operationRepository.findByType(OperationType.ADDITION)).thenReturn(operation);

        // Act
        Double result = operationService.performOperation(request);

        // Assert
        assertEquals(6D, result);
        verify(recordService, times(1)).createRecord(user, operation, 6D, operands);
        verify(userService, times(1)).updateUser(userCaptor.capture());

        User updatedUser = userCaptor.getValue();
        assertEquals(90D, updatedUser.getBalance());
    }

    @Test
    void performOperation_whenAdditionNegativeNumbers_shouldStoreRecord() {

        Double[] operands = new Double[]{-1D, -2D, -3D};
        // Arrange
        OperationRequestDTO request = new OperationRequestDTO();
        request.setUserId(1L);
        request.setType(OperationType.ADDITION);
        request.setNumbers(operands);

        User user = new User();
        user.setId(1L);
        user.setBalance(100D);

        Operation operation = new Operation();
        operation.setId(1L);
        operation.setCost(10D);

        when(userService.getUserById(1L)).thenReturn(user);
        when(operationRepository.findByType(OperationType.ADDITION)).thenReturn(operation);

        // Act
        Double result = operationService.performOperation(request);

        // Assert
        assertEquals(-6D, result);
        verify(recordService, times(1)).createRecord(user, operation, -6D, operands);
        verify(userService, times(1)).updateUser(userCaptor.capture());

        User updatedUser = userCaptor.getValue();
        assertEquals(90D, updatedUser.getBalance());
    }

    @Test
    void performOperation_whenUserDoesNotExist_shouldThrowException() {
        // Arrange
        OperationRequestDTO request = new OperationRequestDTO();
        request.setUserId(1L);
        request.setType(OperationType.ADDITION);
        request.setNumbers(new Double[]{1D, 2D, 3D});

        when(userService.getUserById(1L)).thenReturn(null);

        // Act & Assert
        try {
            operationService.performOperation(request);
        } catch (IllegalArgumentException e) {
            assertEquals("User not found, id: 1", e.getMessage());
        }
    }

    @Test
    void performOperation_whenOperationDoesNotExist_shouldThrowException() {
        // Arrange
        OperationRequestDTO request = new OperationRequestDTO();
        request.setUserId(1L);
        request.setType(OperationType.ADDITION);
        request.setNumbers(new Double[]{1D, 2D, 3D});

        User user = new User();
        user.setId(1L);
        user.setBalance(100D);

        when(userService.getUserById(1L)).thenReturn(user);
        when(operationRepository.findByType(OperationType.ADDITION)).thenReturn(null);

        // Act & Assert
        try {
            operationService.performOperation(request);
        } catch (IllegalArgumentException e) {
            assertEquals("Operation not found, type: ADDITION", e.getMessage());
        }
    }

    @Test
    void performOperation_whenUserDoesNotHaveEnoughBalance_shouldThrowException() {
        // Arrange
        OperationRequestDTO request = new OperationRequestDTO();
        request.setUserId(1L);
        request.setType(OperationType.ADDITION);
        request.setNumbers(new Double[]{1D, 2D, 3D});

        User user = new User();
        user.setId(1L);
        user.setBalance(5D);

        Operation operation = new Operation();
        operation.setId(1L);
        operation.setCost(10D);

        when(userService.getUserById(1L)).thenReturn(user);
        when(operationRepository.findByType(OperationType.ADDITION)).thenReturn(operation);

        // Act & Assert
        try {
            operationService.performOperation(request);
        } catch (IllegalArgumentException e) {
            assertEquals("User does not have enough balance to perform operation, user balance: 5.0, operation cost: 10.0", e.getMessage());
        }
    }

    @Test
    void saveOperation_whenOperationDoesNotExist_shouldSaveOperation() {
        // Arrange
        Operation operation = new Operation();
        operation.setType(OperationType.ADDITION);
        operation.setCost(10D);

        when(operationRepository.findByType(OperationType.ADDITION)).thenReturn(null);
        when(operationRepository.save(operation)).thenReturn(operation);

        // Act
        Operation result = operationService.createOperation(operation);

        // Assert
        assertEquals(operation, result);
        verify(operationRepository, times(1)).save(operation);
    }


}
