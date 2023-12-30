package com.luantang.facebookapi.config.controllers;

import com.luantang.facebookapi.dto.UserDto;
import com.luantang.facebookapi.dto.response.UserResponse;
import com.luantang.facebookapi.services.UserService;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsers")
    public ResponseEntity<UserResponse> getUsers (
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return new ResponseEntity<>(userService.getUsers(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser() {;
        return new ResponseEntity<>(userService.getCurrentUser(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PutMapping("/current/update")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
    }

    @PutMapping("/current/friend/add/{targetUserId}")
    public ResponseEntity<UserDto> addFriendToCurrentUser(@PathVariable("targetUserId") String targetUserId) {
        return new ResponseEntity<>(userService.addFriendToCurrentUser(targetUserId), HttpStatus.OK);
    }

    @PutMapping("/current/friend/remove/{targetUserId}")
    public ResponseEntity<UserDto> removeFriendToCurrentUser(@PathVariable("targetUserId") String targetUserId) {
        return new ResponseEntity<>(userService.removeFriendFromCurrentUser(targetUserId), HttpStatus.OK);
    }

    @GetMapping("/current/friends")
    public ResponseEntity<List<UserDto>> getFriendListFromCurrentUser() {
        return new ResponseEntity<>(userService.getFriendListFromCurrentUser(),HttpStatus.OK);
    }

    @GetMapping("/current/friend/{targetUserId}/isFriend")
    public ResponseEntity<Boolean> isFriendWithCurrentUser(@PathVariable("targetUserId") String targetUserId) {
        return new ResponseEntity<>(userService.isFriendWithCurrentUser(targetUserId), HttpStatus.OK);
    }

    @PutMapping("/{targetUserId}/friend/pending/add")
    public ResponseEntity<UserDto> pendCurrentUserToFriendListOfTargetUser(@PathVariable("targetUserId") String targetUserId) {
        return new ResponseEntity<>(userService.pendCurrentUserToFriendListOfTargetUser(targetUserId), HttpStatus.OK);
    }

    @PutMapping("/{targetUserId}/friend/pending/remove")
    public ResponseEntity<UserDto> offPendCurrentUserFromFriendListOfTargetUser(@PathVariable("targetUserId") String targetUserId) {
        return new ResponseEntity<>(userService.offPendCurrentUserFromFriendListOfTargetUser(targetUserId), HttpStatus.OK);
    }

    @GetMapping("/current/friends/pending")
    public ResponseEntity<List<UserDto>> getPendingFriendListFromCurrentUser() {
        return new ResponseEntity<>(userService.getPendingFriendListFromCurrentUser(),HttpStatus.OK);
    }

    @GetMapping("/{targetUserId}/friend/pending/isOnPending")
    public ResponseEntity<Boolean> isCurrentUserOnPendingWithTargetUser(@PathVariable("targetUserId") String targetUserId) {
        return new ResponseEntity<>(userService.isCurrentUserOnPendingWithTargetUser(targetUserId), HttpStatus.OK);
    }

    @GetMapping("/current/friend/pending/{targetUserId}/isOnPending")
    public ResponseEntity<Boolean> isOnPendingWithCurrentUser(@PathVariable("targetUserId") String targetUserId) {
        return new ResponseEntity<>(userService.isOnPendingWithCurrentUser(targetUserId), HttpStatus.OK);
    }
 }
