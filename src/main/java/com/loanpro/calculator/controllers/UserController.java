package com.loanpro.calculator.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "User APIs")
@RestController
@RequestMapping("/calculator/user")
public class UserController {
    @GetMapping
    public String index() {
        return "Hello Serverless! Greetings from Spring Boot!";
    }
}
