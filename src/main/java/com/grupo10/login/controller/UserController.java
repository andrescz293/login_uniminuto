package com.grupo10.login.controller;

import com.grupo10.login.model.User;
import com.grupo10.login.service.UserService;
import com.grupo10.login.util.ApiResponse;
import com.grupo10.login.util.DataLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);

            ApiResponse<User> response = new ApiResponse<>(HttpStatus.CREATED.value(), createdUser, "User created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            ApiResponse<User> errorResponse = new ApiResponse<>(-1, null, ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);

        if (user != null) {
            ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), user, "User retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<User> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), null, "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        ApiResponse<List<User>> response = new ApiResponse<>(HttpStatus.OK.value(), users, "Users retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable Integer userId, @RequestBody User updatedUser) {
        User savedUser = userService.updateUser(userId, updatedUser);

        if (savedUser != null) {
            ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), savedUser, "User updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<User> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), null, "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateState/{userId}")
    public ResponseEntity<ApiResponse<User>> updateState(@PathVariable Integer userId, @RequestBody User userState) {
        User savedUser = userService.updateUserState(userId, userState.isUserState());

        if (savedUser != null) {
            ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), null, "User state updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<User> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), null, "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<User>> loginUser(@RequestBody DataLogin dataLogin) {
        User authenticatedUser = userService.authenticateUser(dataLogin);

        if (authenticatedUser != null) {
            ApiResponse<User> response = new ApiResponse<>(HttpStatus.OK.value(), authenticatedUser, "User authenticated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<User> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), null, "Invalid credentials");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }





}
