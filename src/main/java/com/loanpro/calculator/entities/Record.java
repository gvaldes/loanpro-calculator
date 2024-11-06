package com.loanpro.calculator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Operation operation;
    @ManyToOne
    private User user;
    private Double amount;
    private Double userBalance;
    private String operationResponse;
    @CreatedDate
    private LocalDateTime createdDate;
    private boolean deleted;
    private String numbers;
}
