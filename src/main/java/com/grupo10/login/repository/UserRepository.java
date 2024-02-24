package com.grupo10.login.repository;


import com.grupo10.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  User findByUserLoginAndUserPassword(String userLogin, String userPassword);

  boolean existsByUserLogin(String userLogin);

  boolean existsByUserEmail(String userEmail);
}
