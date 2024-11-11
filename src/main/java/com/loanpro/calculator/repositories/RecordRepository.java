package com.loanpro.calculator.repositories;

import com.loanpro.calculator.entities.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    Page<Record> findAllByUserId(Long userId, Pageable pageable);
}
