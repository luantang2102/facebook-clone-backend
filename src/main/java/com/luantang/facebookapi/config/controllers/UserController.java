package com.luantang.facebookapi.config.controllers;

import com.luantang.facebookapi.dto.UserDto;
import com.luantang.facebookapi.dto.response.UserResponse;
import com.luantang.facebookapi.services.UserService;
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

    @PutMapping("/current/friend/add/{saveUserId}")
    public ResponseEntity<UserDto> addFriendToCurrentUser(@PathVariable("saveUserId") String saveUserId) {
        return new ResponseEntity<>(userService.addFriendToCurrentUser(saveUserId), HttpStatus.OK);
    }

    @PutMapping("/current/friend/remove/{removeUserId}")
    public ResponseEntity<UserDto> removeFriendToCurrentUser(@PathVariable("removeUserId") String removeUserId) {
        return new ResponseEntity<>(userService.removeFriendToCurrentUser(removeUserId), HttpStatus.OK);
    }

    @GetMapping("/current/friends")
    public ResponseEntity<List<UserDto>> getFriendListFromCurrentUser() {
        return new ResponseEntity<>(userService.getFriendListFromCurrentUser(),HttpStatus.OK);
    }
 }
