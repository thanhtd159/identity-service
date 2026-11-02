package com.devspring.identity_service.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devspring.identity_service.dto.request.UserCreationRequest;
import com.devspring.identity_service.dto.request.UserUpdateRequest;
import com.devspring.identity_service.dto.response.UserResponse;
import com.devspring.identity_service.entity.User;
import com.devspring.identity_service.exception.AppException;
import com.devspring.identity_service.exception.ErrorCode;
import com.devspring.identity_service.mapper.UserMapper;
import com.devspring.identity_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    // PasswordEncoder passwordEncoder;

    public UserResponse createUser(UserCreationRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // var user = new User();
        // user.setUsername(request.getUsername());
        // user.setFirstName(request.getFirstName());
        // user.setLastName(request.getLastName());
        // user.setPassword(request.getPassword());
        // user.setDob(request.getDob());

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse getUserById(String id) {
        return userMapper
                .toUserResponse(
                        userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public UserResponse updateUserById(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, request);

        // user.setFirstName(request.getFirstName());
        // user.setLastName(request.getLastName());
        // user.setPassword(request.getPassword());
        // user.setDob(request.getDob());

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    // public User getUserByUsername(String username) {
    // return userRepository.findByUsername(username);
    // }
}
