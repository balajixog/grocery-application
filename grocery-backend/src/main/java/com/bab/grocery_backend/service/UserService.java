package com.bab.grocery_backend.service;

import com.bab.grocery_backend.dto.dtoRequest.UpdateProfileRequestDto;
import com.bab.grocery_backend.dto.dtoRequest.UserProfileResponseDto;
import com.bab.grocery_backend.entity.User;

public interface UserService {

    User createUser(User user);
    UserProfileResponseDto getProfile(String email);
    UserProfileResponseDto updateProfile(String email, UpdateProfileRequestDto dto);
    void deleteAccount(String email);

}
