package com.loanpro.calculator.services.impl;

import com.loanpro.calculator.entities.Operation;
import com.loanpro.calculator.entities.Record;
import com.loanpro.calculator.entities.User;
import com.loanpro.calculator.repositories.RecordRepository;
import com.loanpro.calculator.services.RecordService;
import com.loanpro.calculator.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;
    private final UserService userService;

    public RecordServiceImpl(RecordRepository recordRepository, UserService userService) {
        this.recordRepository = recordRepository;
        this.userService = userService;
    }

    @Override
    public Record createRecord(User user, Operation operation, Double result, Double... operands) {
        log.info("Creating record for user: {}, operation: {}, result: {}, operands: {}", user, operation, result, Arrays.toString(operands));

        Record record = new Record();
        record.setUser(user);
        record.setOperation(operation);
        record.setAmount(operation.getCost());
        record.setUserBalance(user.getBalance());
        record.setOperationResponse(result.toString());
        record.setNumbers(Arrays.toString(operands));

        return recordRepository.save(record);
    }

    @Override
    public Record createRecord(User user, Operation operation, String result) {
        log.info("Creating record for user: {}, operation: {}, result: {}", user, operation, result);
        Record record = new Record();
        record.setUser(user);
        record.setOperation(operation);
        record.setAmount(operation.getCost());
        record.setUserBalance(user.getBalance());
        record.setOperationResponse(result);

        return recordRepository.save(record);
    }


    @Override
    public List<Record> getRecordsByUser(Long userId) {
        log.info("Getting records by user: {}", userId);

        User user = userService.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        return recordRepository.findAllByUserId(userId);
    }
}
