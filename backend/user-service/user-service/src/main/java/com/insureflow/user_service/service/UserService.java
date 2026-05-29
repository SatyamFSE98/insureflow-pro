package com.insureflow.user_service.service;

import com.insureflow.user_service.dto.request.LoginRequestDto;
import com.insureflow.user_service.dto.request.LoginResponseDto;
import com.insureflow.user_service.dto.request.RegisterUserRequest;
import com.insureflow.user_service.dto.response.PageResponse;
import com.insureflow.user_service.dto.response.UserResponse;

public interface UserService {

    UserResponse registerUser(RegisterUserRequest request);

    LoginResponseDto loginUser(LoginRequestDto loginRequestDto);

    UserResponse getUserById(Long userId);

    PageResponse<UserResponse> getAllUsers(
            int pageNo,
            int pageSize,
            String sortBy,
            String sortDir
    );


}
