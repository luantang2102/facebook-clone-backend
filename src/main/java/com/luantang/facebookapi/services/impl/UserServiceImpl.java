package com.luantang.facebookapi.services.impl;

import com.luantang.facebookapi.dto.UserDto;
import com.luantang.facebookapi.dto.response.UserResponse;
import com.luantang.facebookapi.exceptions.UserNotFoundException;
import com.luantang.facebookapi.models.UserEntity;
import com.luantang.facebookapi.repositories.UserRepository;
import com.luantang.facebookapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse getUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserEntity> users = userRepository.findAll(pageable);
        List<UserEntity> userList = users.getContent();
        List<UserDto> content = userList.stream().map(user -> mapToDto(user)).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(content);
        userResponse.setPageNo(users.getNumber());
        userResponse.setPageSize(users.getSize());
        userResponse.setLast(users.isLast());
        userResponse.setTotalElements(users.getTotalElements());
        userResponse.setTotalPages(users.getTotalPages());

        return userResponse;
    }

    @Override
    public UserDto getCurrentUser() {
        return mapToDto((UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Override
    public UserDto getUserByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User with associate email could not be found"));
        return mapToDto(user);
    }

    @Override
    public UserDto getUserById(String userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User could not be found"));
        return mapToDto(user);
    }

    @Override
    public UserDto createUser(UserEntity user) {
        userRepository.save(user);
        return mapToDto(user);
    }

    private UserDto mapToDto(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUserName(user.getUsername());
        userDto.setUserImage(user.getUserImage());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setActivityStatus(user.isActive());
        userDto.setJoiningDate(user.getJoiningDate());
        return userDto;
    }
}
