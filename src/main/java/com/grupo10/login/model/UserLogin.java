package com.grupo10.login.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_login")

public class UserLogin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long  userId;

  @Column(name = "user_name", nullable = false, length = 255)
  private String userName;

  @Column(name = "user_lastname", nullable = true, length = 255)
  private String userLastname;

  @Column(name = "user_email", nullable = false, length = 255)
  private String userEmail;

  @Column(name = "user_login", nullable = false, length = 15)
  private String userLogin;

  @Column(name = "user_password", nullable = false, length = 15)
  private String userPassword;

  @Column(name = "user_state", nullable = false)
  private boolean userState;
}
