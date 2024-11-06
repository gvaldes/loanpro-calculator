package com.loanpro.calculator.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@Tag(name = "User", description = "User APIs")
@RestController
@RequestMapping("/calculator/user")
public class UserController {
}
