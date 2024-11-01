package com.loanpro.calculator.services;

import com.loanpro.calculator.entities.Operation;
import com.loanpro.calculator.entities.Record;
import com.loanpro.calculator.entities.User;

import java.util.List;

public interface RecordService {
    Record createRecord(Record record);
    Record createRecord(User user, Operation operation, Double result, Double... operands);
    List<Record> getRecordsByUser(Long userId);
}
