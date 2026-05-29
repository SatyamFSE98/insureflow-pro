package com.insureflow.user_service.service;

import com.insureflow.user_service.dto.request.RegisterUserRequest;
import com.insureflow.user_service.dto.response.UserResponse;

public interface UserService {

    UserResponse registerUser(RegisterUserRequest request);
}
