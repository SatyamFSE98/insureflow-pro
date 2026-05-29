package com.insureflow.user_service.service;

import com.insureflow.user_service.constant.AppConstants;
import com.insureflow.user_service.dto.request.LoginRequestDto;
import com.insureflow.user_service.dto.request.LoginResponseDto;
import com.insureflow.user_service.dto.request.RegisterUserRequest;
import com.insureflow.user_service.dto.response.UserResponse;
import com.insureflow.user_service.entity.User;
import com.insureflow.user_service.exception.InvalidCredentialsException;
import com.insureflow.user_service.exception.ResourceAlreadyExistsException;
import com.insureflow.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserResponse registerUser(RegisterUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new ResourceAlreadyExistsException(AppConstants.EMAIL_ALREADY_EXISTS);
        }

        if(userRepository.existsByMobileNumber(request.getMobileNumber())){
            throw new ResourceAlreadyExistsException(AppConstants.MOBILE_ALREADY_EXISTS);
        }

        User user  = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .mobileNumber(request.getMobileNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();

        User savedUser = userRepository.save(user);
        return mapToUserResponse(savedUser);

    }

    @Override
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
         User user = userRepository.findByEmail(loginRequestDto.getEmail())
                 .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

         boolean passwordMatches = passwordEncoder.matches(loginRequestDto.getPassword(),user.getPassword());

         if(!passwordMatches){
             throw new InvalidCredentialsException("Invalid email or Password");
         }
         if(!user.isActive()){
             throw new InvalidCredentialsException("User account is inactive");
         }
        return mapToLoginResponse(user);
    }

    private LoginResponseDto mapToLoginResponse(User user) {
        return  LoginResponseDto.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .active(user.isActive())
                .build();
    }

    private UserResponse mapToUserResponse(User user) {

        return UserResponse.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .mobileNumber(user.getMobileNumber())
                .role(user.getRole())
                .active(user.isActive())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
