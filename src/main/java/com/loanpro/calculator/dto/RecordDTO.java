package com.loanpro.calculator.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RecordDTO {
    String id;
    String operation;
    String result;
    String cost;
    String createdDate;
    String balance;
}
