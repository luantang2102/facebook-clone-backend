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

import java.util.ArrayList;
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
        //Already mapped from RegisterDto to UserEntity
        //Not call directly from UserController
        userRepository.save(user);
        return mapToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        //This userDto only contain userImage, userCoverImage and userName
        UserEntity user = userRepository.findById(getCurrentUser().getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setUserName(userDto.getUserName());
        user.setUserImage(userDto.getUserImage());
        user.setCoverImage(userDto.getUserCoverImage());

        UserEntity updateUser = userRepository.save(user);

        return mapToDto(updateUser);
    }

    @Override
    public UserDto addFriendToCurrentUser(String saveUserId) {
        UserEntity user = userRepository.findById(getCurrentUser().getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!isFriend(saveUserId)) {
            List<String> newFriendIdList = user.getFriendIdList();
            newFriendIdList.add(saveUserId);
            user.setFriendIdList(newFriendIdList);

            UserEntity updateUser = userRepository.save(user);

            return mapToDto(updateUser);
        }
        else {
            throw new UserNotFoundException("This user is your friend already");
        }
    }

    @Override
    public UserDto removeFriendToCurrentUser(String removeUserId) {
        UserEntity user = userRepository.findById(getCurrentUser().getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        if(isFriend(removeUserId)) {
            List<String> newFriendIdList = user.getFriendIdList();
            newFriendIdList.remove(removeUserId);
            user.setFriendIdList(newFriendIdList);

            UserEntity updateUser = userRepository.save(user);

            return mapToDto(updateUser);
        }
        else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public boolean isFriend(String targetUserId) {
        UserEntity user = userRepository.findById(getCurrentUser().getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<String> friendIdList = user.getFriendIdList();
        return friendIdList.stream().anyMatch(id -> id.equals(targetUserId));
    }

    @Override
    public List<UserDto> getFriendListFromCurrentUser() {
        UserEntity user = userRepository.findById(getCurrentUser().getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        List<UserEntity> friendList = new ArrayList<>();
        for(String friendId : user.getFriendIdList()) {
            friendList.add(userRepository.findById(friendId).orElseThrow(() -> new UserNotFoundException("User not found")));
        }
        List<UserDto> content = friendList.stream().map(friend -> mapToDto(friend)).collect(Collectors.toList());
        return content;
    }

    private UserDto mapToDto(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUserName(user.getUsername());
        userDto.setUserImage(user.getUserImage());
        userDto.setUserCoverImage(user.getCoverImage());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setActivityStatus(user.isActive());
        userDto.setJoiningDate(user.getJoiningDate());
        userDto.setFriendIdList(user.getFriendIdList());
        return userDto;
    }
}
