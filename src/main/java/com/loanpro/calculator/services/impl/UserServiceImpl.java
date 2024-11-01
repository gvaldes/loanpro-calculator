package com.loanpro.calculator.services.impl;

import com.loanpro.calculator.entities.User;
import com.loanpro.calculator.repositories.UserRepository;
import com.loanpro.calculator.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(Long id) {
        log.info("Getting user by id: {}", id);
        return userRepository.findById(id).orElse(null);
    }


    @Override
    public User updateUser(User user) {
        log.info("Updating user: {}", user);
        return userRepository.save(user);
    }
}
