package com.luantang.facebookapi.services.impl;

import com.luantang.facebookapi.dto.UserDto;
import com.luantang.facebookapi.dto.response.UserResponse;
import com.luantang.facebookapi.exceptions.UserNotFoundException;
import com.luantang.facebookapi.models.Friend;
import com.luantang.facebookapi.models.UserEntity;
import com.luantang.facebookapi.models.enums.FriendStatus;
import com.luantang.facebookapi.repositories.UserRepository;
import com.luantang.facebookapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    public UserDto addFriendToCurrentUser(String targetUserId) {
        UserEntity currentUser = userRepository.findById(getCurrentUser().getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        UserEntity targetUser = userRepository.findById(targetUserId).orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!isFriendWithCurrentUser(targetUserId)) {
            if(isOnPendingWithCurrentUser(targetUserId)) {
                //Add current user to friend list of target user
                List<Friend> newTargetUserFriendIdList = targetUser.getFriendIdList();
                newTargetUserFriendIdList.add(new Friend(currentUser.getUserId(), FriendStatus.FRIEND, new Date()));
                targetUser.setFriendIdList(newTargetUserFriendIdList);
                targetUser.setTotalFriends(targetUser.getTotalFriends() + 1);
                userRepository.save(targetUser);

                //Add target user to friend list of current user and return dto of current user
                List<Friend> newCurrentUserFriendIdList = currentUser.getFriendIdList();
                newCurrentUserFriendIdList.stream()
                        .filter(friend -> friend.getUserId().equals(targetUserId))
                        .findFirst()
                        .ifPresent(friend -> {
                            friend.setFriendStatus(FriendStatus.FRIEND);
                            friend.setConnectDate(new Date());
                        });
                currentUser.setFriendIdList(newCurrentUserFriendIdList);
                currentUser.setTotalFriends(currentUser.getTotalFriends() + 1);
                UserEntity updateCurrentUser = userRepository.save(currentUser);

                return mapToDto(updateCurrentUser);
            }
            else {
                throw new UserNotFoundException("This user not found on pending list");
            }
        }
        else {
            throw new UserNotFoundException("This user is your friend already");
        }
    }

    @Override
    public UserDto removeFriendFromCurrentUser(String targetUserId) {
        UserEntity currentUser = userRepository.findById(getCurrentUser().getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        UserEntity targetUser = userRepository.findById(targetUserId).orElseThrow(() -> new UserNotFoundException("User not found"));

        if(isFriendWithCurrentUser(targetUserId)) {
            //Remove current user from friend list of target user
            List<Friend> newTargetFriendIdList = targetUser.getFriendIdList();
            newTargetFriendIdList.removeIf(friend -> friend.getFriendStatus() == FriendStatus.FRIEND && friend.getUserId().equals(currentUser.getUserId()));
            targetUser.setFriendIdList(newTargetFriendIdList);
            targetUser.setTotalFriends(targetUser.getTotalFriends() - 1);
            userRepository.save(targetUser);

            //Remove target user from friend list of current user and return dto of current user
            List<Friend> newCurrentFriendIdList = currentUser.getFriendIdList();
            newCurrentFriendIdList.removeIf(friend -> friend.getFriendStatus() == FriendStatus.FRIEND && friend.getUserId().equals(targetUserId));
            currentUser.setFriendIdList(newCurrentFriendIdList);
            currentUser.setTotalFriends(currentUser.getTotalFriends() - 1);
            UserEntity updateUser = userRepository.save(currentUser);

            return mapToDto(updateUser);
        }
        else {
            throw new UserNotFoundException("This user is not your friend");
        }
    }

    @Override
    public UserDto pendCurrentUserToFriendListOfTargetUser(String targetUserId) {
        UserEntity targetUser = userRepository.findById(targetUserId).orElseThrow(() -> new UserNotFoundException("User not found"));
        UserEntity currentUser = userRepository.findById(getCurrentUser().getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        if(!isCurrentUserOnPendingWithTargetUser(targetUserId)) {
            if(!isFriendWithCurrentUser(targetUserId)) {
                List<Friend> newPendingFriendIdList = targetUser.getFriendIdList();
                newPendingFriendIdList.add(new Friend(currentUser.getUserId(), FriendStatus.PENDING, new Date()));
                targetUser.setFriendIdList(newPendingFriendIdList);

                UserEntity updateTargetUser = userRepository.save(targetUser);

                return mapToDto(updateTargetUser);
            }
            else {
                throw new UserNotFoundException("This user is your friend already");
            }
        }
        else {
            throw new UserNotFoundException("You are on pending already");
        }
    }

    @Override
    public UserDto offPendCurrentUserFromFriendListOfTargetUser(String targetUserId) {
        UserEntity targetUser = userRepository.findById(targetUserId).orElseThrow(() -> new UserNotFoundException("User not found"));
        UserEntity currentUser = userRepository.findById(getCurrentUser().getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        if(isCurrentUserOnPendingWithTargetUser(targetUserId)) {
            List<Friend> newFriendIdList = targetUser.getFriendIdList();

            newFriendIdList.removeIf(friend -> friend.getFriendStatus() == FriendStatus.PENDING && friend.getUserId().equals(currentUser.getUserId()));

            targetUser.setFriendIdList(newFriendIdList);

            UserEntity updateTargetUser = userRepository.save(targetUser);

            return mapToDto(updateTargetUser);

        }
        else {
            throw new UserNotFoundException("You are not on pending");
        }
    }

    @Override
    public boolean isFriendWithCurrentUser(String targetUserId) {
        userRepository.findById(targetUserId).orElseThrow(() -> new UserNotFoundException("User not found"));

        UserEntity currentUser = userRepository.findById(getCurrentUser().getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        return currentUser.getFriendIdList()
                .stream()
                .anyMatch(friend -> (friend.getFriendStatus() == FriendStatus.FRIEND) && friend.getUserId().equals(targetUserId));
    }

    @Override
    public boolean isOnPendingWithCurrentUser(String targetUserId) {
        userRepository.findById(targetUserId).orElseThrow(() -> new UserNotFoundException("User not found"));

        UserEntity currentUser = userRepository.findById(getCurrentUser().getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        return currentUser.getFriendIdList()
                .stream()
                .anyMatch(friend -> (friend.getFriendStatus() == FriendStatus.PENDING) && friend.getUserId().equals(targetUserId));
    }

    @Override
    public boolean isCurrentUserOnPendingWithTargetUser(String targetUserId) {
        UserEntity targetUser = userRepository.findById(targetUserId).orElseThrow(() -> new UserNotFoundException("User not found"));

        UserEntity currentUser = userRepository.findById(getCurrentUser().getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        return targetUser.getFriendIdList()
                .stream()
                .anyMatch(friend -> (friend.getFriendStatus() == FriendStatus.PENDING) && friend.getUserId().equals(currentUser.getUserId()));
    }

    @Override
    public List<UserDto> getFriendListFromCurrentUser() {
        UserEntity currentUser = userRepository.findById(getCurrentUser().getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        List<UserEntity> friendList = new ArrayList<>();
        for(Friend friend : currentUser.getFriendIdList()) {
            if(friend.getFriendStatus() == FriendStatus.FRIEND) {
                friendList.add(userRepository.findById(friend.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found")));
            }
        }
        List<UserDto> content = friendList.stream().map(friend -> mapToDto(friend)).collect(Collectors.toList());
        return content;
    }

    @Override
    public List<UserDto> getPendingFriendListFromCurrentUser() {
        UserEntity currentUser = userRepository.findById(getCurrentUser().getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));

        List<UserEntity> friendList = new ArrayList<>();
        for(Friend friend : currentUser.getFriendIdList()) {
            if(friend.getFriendStatus() == FriendStatus.PENDING) {
                friendList.add(userRepository.findById(friend.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found")));
            }
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
        userDto.setTotalFriends(user.getTotalFriends());
        userDto.setFriendIdList(user.getFriendIdList());
        return userDto;
    }
}
