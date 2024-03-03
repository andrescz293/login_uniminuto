package com.grupo10.login.repository;


import com.grupo10.login.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserLogin, Integer> {
  UserLogin findByUserLoginAndUserPassword(String userLogin, String userPassword);

  @Query("SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM UserLogin u WHERE u.userLogin = :userLogin")
  boolean existsByUserLogin(@Param("userLogin") String userLogin);

  boolean existsByUserEmail(String userEmail);
}
