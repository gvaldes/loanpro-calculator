package com.loanpro.calculator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
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
    private LocalDateTime date;
    private boolean deleted;
    private String operands;
}
