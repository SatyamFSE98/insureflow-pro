package com.insureflow.user_service.controller;


import com.insureflow.user_service.constant.AppConstants;
import com.insureflow.user_service.dto.request.LoginRequestDto;
import com.insureflow.user_service.dto.request.LoginResponseDto;
import com.insureflow.user_service.dto.request.RegisterUserRequest;
import com.insureflow.user_service.dto.request.UpdateUserRequest;
import com.insureflow.user_service.dto.response.ApiResponse;
import com.insureflow.user_service.dto.response.PageResponse;
import com.insureflow.user_service.dto.response.UserResponse;
import com.insureflow.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long userId){
           UserResponse userResponse = userService.getUserById(userId);

           ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                   .data(userResponse)
                   .message("user fetched successfully")
                   .success(true)
                   .timestamp(LocalDateTime.now())
                   .build();

           return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "userId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        PageResponse<UserResponse> users = userService.getAllUsers(pageNo,pageSize,sortBy,sortDir);

        ApiResponse<PageResponse<UserResponse>> response = ApiResponse.<PageResponse<UserResponse>>builder()
                .data(users)
                .message("Users fetched Successfully")
                .success(true)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);

    }

     @PutMapping("/{userId}")
     public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserRequest updateUserRequest){
        UserResponse userResponse = userService.updateUser(userId,updateUserRequest);

        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .data(userResponse)
                .success(true)
                .message("user updated succesfully")
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
     }

     @PatchMapping("/{userId}/deactivate")
     public ResponseEntity<ApiResponse<UserResponse>> deactivateUser(@PathVariable Long userId){
         UserResponse deactivateUser = userService.deactivateUser(userId);

         ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                 .data(deactivateUser)
                 .message("User deactivated successful")
                 .success(true)
                 .timestamp(LocalDateTime.now())
                 .build();

         return ResponseEntity.ok(response);
     }

}
