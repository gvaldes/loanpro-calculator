package com.loanpro.calculator.services;

import com.loanpro.calculator.entities.User;

public interface UserService {
    User getUserById(Long id);
    void reduceBalance(User user, Double amount);
}
