package com.grupo10.login.service;

import com.grupo10.login.model.UserLogin;
import com.grupo10.login.util.DataLogin;

import java.util.List;

public interface UserService {
    UserLogin updateUser(Integer userId, UserLogin updatedUserLogin);

    UserLogin updateUserState(Integer userId, Boolean userState);

    List<UserLogin> getAllUsers();

    UserLogin getUserById(Integer userId);

    UserLogin createUser(UserLogin userLogin);

    UserLogin authenticateUser(DataLogin dataLogin);
}
