package com.insureflow.user_service.controller;


import com.insureflow.user_service.constant.AppConstants;
import com.insureflow.user_service.dto.request.LoginRequestDto;
import com.insureflow.user_service.dto.request.LoginResponseDto;
import com.insureflow.user_service.dto.request.RegisterUserRequest;
import com.insureflow.user_service.dto.response.ApiResponse;
import com.insureflow.user_service.dto.response.UserResponse;
import com.insureflow.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(@Valid  @RequestBody RegisterUserRequest request){

       UserResponse userResponse = userService.registerUser(request);

       ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
               .success(true)
               .message(AppConstants.USER_REGISTERED_SUCCESSFULLY)
               .data(userResponse)
               .timestamp(LocalDateTime.now())
               .build();

       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public  ResponseEntity<ApiResponse<LoginResponseDto>> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto){
       LoginResponseDto loginResponseDto = userService.loginUser(loginRequestDto);

       ApiResponse<LoginResponseDto> response = ApiResponse.<LoginResponseDto>builder().
                                                 data(loginResponseDto)
               .message("login successful")
               .success(true)
               .timestamp(LocalDateTime.now())
               .build();

       return ResponseEntity.ok(response);
    }

}
