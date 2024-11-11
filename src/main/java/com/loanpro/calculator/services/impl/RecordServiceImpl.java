package com.loanpro.calculator.services.impl;

import com.loanpro.calculator.dto.RecordDTO;
import com.loanpro.calculator.entities.Operation;
import com.loanpro.calculator.entities.Record;
import com.loanpro.calculator.entities.User;
import com.loanpro.calculator.repositories.RecordRepository;
import com.loanpro.calculator.services.RecordService;
import com.loanpro.calculator.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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
    public Page<RecordDTO> findAllByUserId(Long userId, Pageable pageable){
        log.info("Getting records by user: {}", userId);

        User user = userService.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return recordRepository.findAllByUserId(userId, pageable).map(record -> {
            RecordDTO recordDTO = new RecordDTO();
            recordDTO.setId(String.valueOf(record.getId()));
            recordDTO.setOperation(String.valueOf(record.getOperation().getType()));
            recordDTO.setCost(String.valueOf(record.getOperation().getCost()));
            recordDTO.setResult(record.getOperationResponse());
            recordDTO.setCreatedDate(record.getCreatedDate().format(formatter));
            recordDTO.setBalance(String.valueOf(record.getUserBalance()));
            return recordDTO;
        });
    }
}
