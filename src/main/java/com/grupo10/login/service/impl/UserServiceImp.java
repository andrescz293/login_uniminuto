package com.grupo10.login.service.impl;

import com.grupo10.login.model.User;
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
    public User createUser(User user) {
        if (userRepository.existsByUserLogin(user.getUserLogin())) {
            throw new RuntimeException("User with this login already exists");
        }
        if (userRepository.existsByUserEmail(user.getUserEmail())) {
            throw new RuntimeException("User with this email already exists");
        }
        user.setUserState(true);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User updateUser(Integer userId, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setUserName(updatedUser.getUserName());
            userToUpdate.setUserEmail(updatedUser.getUserEmail());
            return userRepository.save(userToUpdate);
        } else {
            return null;
        }
    }

    @Override
    public User updateUserState(Integer userId, Boolean userState) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setUserState(userState);
            return userRepository.save(userToUpdate);
        } else {
            return null;
        }
    }

    @Override
    public User authenticateUser(DataLogin dataLogin) {
        return userRepository.findByUserLoginAndUserPassword(dataLogin.getUserLogin(), dataLogin.getUserPassword());
    }


}
