package com.grupo10.login.service;

import com.grupo10.login.model.User;
import com.grupo10.login.util.DataLogin;

import java.util.List;

public interface UserService {
    User updateUser(Integer userId, User updatedUser);

    User updateUserState(Integer userId, Boolean userState);

    List<User> getAllUsers();

    User getUserById(Integer userId);

    User createUser(User user);

    User authenticateUser(DataLogin dataLogin);
}
