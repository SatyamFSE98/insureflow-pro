package com.insureflow.user_service.service;

import com.insureflow.user_service.constant.AppConstants;
import com.insureflow.user_service.dto.request.LoginRequestDto;
import com.insureflow.user_service.dto.request.LoginResponseDto;
import com.insureflow.user_service.dto.request.RegisterUserRequest;
import com.insureflow.user_service.dto.request.UpdateUserRequest;
import com.insureflow.user_service.dto.response.PageResponse;
import com.insureflow.user_service.dto.response.UserResponse;
import com.insureflow.user_service.entity.User;
import com.insureflow.user_service.exception.InvalidCredentialsException;
import com.insureflow.user_service.exception.ResourceAlreadyExistsException;
import com.insureflow.user_service.exception.UserNotFoundException;
import com.insureflow.user_service.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                "User not found with id: " + userId
        ));

        return mapToUserResponse(user);
    }

    @Override
    public PageResponse<UserResponse> getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort =  sortDir.equalsIgnoreCase("desc")? Sort.by(sortBy).descending()
                        :Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponse>  users = userPage.getContent().stream()
                .map(this::mapToUserResponse).collect(Collectors.toList());
        return PageResponse.<UserResponse>builder()
                .content(users)
                .pageNo(userPage.getNumber())
                .pageSize(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .last(userPage.isLast())
                .build();
    }

    @Override
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
           User user = userRepository.findById(userId).orElseThrow(() ->new UserNotFoundException(
                   "User not found with id: " + userId
           ));

           boolean mobileExistsForOtherUser = userRepository
                    .existsByMobileNumberAndUserIdNot(request.getMobileNumber(), userId);

           if(mobileExistsForOtherUser){
               throw new ResourceAlreadyExistsException("Mobile number already exists");
           }

           user.setFullName(request.getFullName());
           user.setMobileNumber(request.getMobileNumber());
           user.setUpdatedAt(LocalDateTime.now());

           User updatedUser = userRepository.save(user);

           return mapToUserResponse(updatedUser);
    }


    @Override
    public UserResponse deactivateUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new UserNotFoundException("User not found with id:"+userId));
        user.setActive(false);
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return mapToUserResponse(updatedUser);
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
