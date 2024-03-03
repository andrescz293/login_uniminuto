package com.grupo10.login.controller;

import com.grupo10.login.model.UserLogin;
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
    public ResponseEntity<ApiResponse<UserLogin>> createUser(@RequestBody UserLogin userLogin) {
        try {
            UserLogin createdUserLogin = userService.createUser(userLogin);

            ApiResponse<UserLogin> response = new ApiResponse<>(HttpStatus.CREATED.value(), createdUserLogin, "User created successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            ApiResponse<UserLogin> errorResponse = new ApiResponse<>(-1, null, ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserLogin>> getUserById(@PathVariable Integer userId) {
        UserLogin userLogin = userService.getUserById(userId);

        if (userLogin != null) {
            ApiResponse<UserLogin> response = new ApiResponse<>(HttpStatus.OK.value(), userLogin, "User retrieved successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<UserLogin> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), null, "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<UserLogin>>> getAllUsers() {
        List<UserLogin> userLogins = userService.getAllUsers();
        ApiResponse<List<UserLogin>> response = new ApiResponse<>(HttpStatus.OK.value(), userLogins, "Users retrieved successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<ApiResponse<UserLogin>> updateUser(@PathVariable Integer userId, @RequestBody UserLogin updatedUserLogin) {
        UserLogin savedUserLogin = userService.updateUser(userId, updatedUserLogin);

        if (savedUserLogin != null) {
            ApiResponse<UserLogin> response = new ApiResponse<>(HttpStatus.OK.value(), savedUserLogin, "User updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<UserLogin> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), null, "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateState/{userId}")
    public ResponseEntity<ApiResponse<UserLogin>> updateState(@PathVariable Integer userId, @RequestBody UserLogin userLoginState) {
        UserLogin savedUserLogin = userService.updateUserState(userId, userLoginState.isUserState());

        if (savedUserLogin != null) {
            ApiResponse<UserLogin> response = new ApiResponse<>(HttpStatus.OK.value(), null, "User state updated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<UserLogin> response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(), null, "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLogin>> loginUser(@RequestBody DataLogin dataLogin) {
        UserLogin authenticatedUserLogin = userService.authenticateUser(dataLogin);

        if (authenticatedUserLogin != null) {
            ApiResponse<UserLogin> response = new ApiResponse<>(HttpStatus.OK.value(), authenticatedUserLogin, "User authenticated successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<UserLogin> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), null, "Invalid credentials");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }





}
