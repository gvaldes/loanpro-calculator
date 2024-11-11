package com.loanpro.calculator.services;

import com.loanpro.calculator.dto.RecordDTO;
import com.loanpro.calculator.entities.Operation;
import com.loanpro.calculator.entities.Record;
import com.loanpro.calculator.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecordService {
    Record createRecord(User user, Operation operation, Double result, Double... operands);
    Record createRecord(User user, Operation operation, String result);
    Page<RecordDTO> findAllByUserId(Long userId, Pageable pageable);
}
