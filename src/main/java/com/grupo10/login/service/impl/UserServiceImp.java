package com.grupo10.login.service.impl;

import com.grupo10.login.model.UserLogin;
import com.grupo10.login.repository.UserRepository;
import com.grupo10.login.service.UserService;
import com.grupo10.login.util.DataLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserLogin createUser(UserLogin userLogin) {
        if (userRepository.existsByUserLogin(userLogin.getUserLogin())) {
            throw new RuntimeException("User with this login already exists");
        }
        if (userRepository.existsByUserEmail(userLogin.getUserEmail())) {
            throw new RuntimeException("User with this email already exists");
        }
        userLogin.setUserState(true);
        return userRepository.save(userLogin);
    }

    @Override
    public List<UserLogin> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserLogin getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public UserLogin updateUser(Integer userId, UserLogin updatedUserLogin) {
        Optional<UserLogin> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            UserLogin userLoginToUpdate = existingUser.get();
            userLoginToUpdate.setUserName(updatedUserLogin.getUserName());
            userLoginToUpdate.setUserEmail(updatedUserLogin.getUserEmail());
            return userRepository.save(userLoginToUpdate);
        } else {
            return null;
        }
    }

    @Override
    public UserLogin updateUserState(Integer userId, Boolean userState) {
        Optional<UserLogin> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            UserLogin userLoginToUpdate = existingUser.get();
            userLoginToUpdate.setUserState(userState);
            return userRepository.save(userLoginToUpdate);
        } else {
            return null;
        }
    }

    @Override
    public UserLogin authenticateUser(DataLogin dataLogin) {
        return userRepository.findByUserLoginAndUserPassword(dataLogin.getUserLogin(), dataLogin.getUserPassword());
    }


}
