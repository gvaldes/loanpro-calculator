package com.loanpro.calculator.repositories;

import com.loanpro.calculator.entities.Operation;
import com.loanpro.calculator.enums.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    Operation findByType(OperationType type);
}
