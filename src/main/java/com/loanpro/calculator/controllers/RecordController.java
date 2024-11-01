package com.loanpro.calculator.controllers;

import com.loanpro.calculator.entities.Record;
import com.loanpro.calculator.services.RecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name="Record", description = "Record APIs")
@RestController
@RequestMapping("/calculator/record")
public class RecordController {
    private final RecordService recordService;

    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }

    @io.swagger.v3.oas.annotations.Operation(summary = "Get all records by user", description = "Get all records by user")
    @GetMapping("/{userId}")
    public ResponseEntity<List<Record>> getRecordsByUser(@PathVariable Long userId) {
        List<Record> records = recordService.getRecordsByUser(userId);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }
}
