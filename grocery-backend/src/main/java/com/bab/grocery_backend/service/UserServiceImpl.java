package com.bab.grocery_backend.service;

import org.springframework.stereotype.Service;

import com.bab.grocery_backend.entity.User;
import com.bab.grocery_backend.repository.UserRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
