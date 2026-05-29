package com.insureflow.user_service.service;

import com.insureflow.user_service.dto.request.LoginRequestDto;
import com.insureflow.user_service.dto.request.LoginResponseDto;
import com.insureflow.user_service.dto.request.RegisterUserRequest;
import com.insureflow.user_service.dto.response.UserResponse;

public interface UserService {

    UserResponse registerUser(RegisterUserRequest request);
    LoginResponseDto loginUser(LoginRequestDto loginRequestDto);
}
