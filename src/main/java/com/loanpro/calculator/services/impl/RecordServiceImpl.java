package com.loanpro.calculator.services.impl;

import com.loanpro.calculator.entities.Operation;
import com.loanpro.calculator.entities.Record;
import com.loanpro.calculator.entities.User;
import com.loanpro.calculator.repositories.RecordRepository;
import com.loanpro.calculator.services.RecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;

    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public Record createRecord(Record record) {
        return recordRepository.save(record);
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
        record.setOperands(Arrays.toString(operands));
        record.setDate(LocalDateTime.now());

        return recordRepository.save(record);
    }

    @Override
    public List<Record> getRecordsByUser(Long userId) {
        return recordRepository.findAllByUserId(userId);
    }
}
