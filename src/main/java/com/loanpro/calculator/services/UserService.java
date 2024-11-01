package com.loanpro.calculator.services;

import com.loanpro.calculator.entities.User;

public interface UserService {
    User getUserById(Long id);
    User updateUser(User user);
}
