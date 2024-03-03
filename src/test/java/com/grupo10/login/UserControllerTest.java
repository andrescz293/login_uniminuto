package com.grupo10.login;

import com.grupo10.login.controller.UserController;
import com.grupo10.login.model.UserLogin;
import com.grupo10.login.service.UserService;
import com.grupo10.login.util.ApiResponse;
import com.grupo10.login.util.DataLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateUser() {
        /*Iniciación clase de respuesta*/
        UserLogin userLogin = new UserLogin(
                1L,
                "Juan",
                "Rodriguez",
                "correo@correo.com",
                "jrcruz",
                "123",
                true);
        when(userService.createUser(any(UserLogin.class))).thenReturn(userLogin);

        ResponseEntity<ApiResponse<UserLogin>> response = userController.createUser(userLogin);

        /*Validar retorno de metodo*/
        verify(userService, times(1)).createUser(any(UserLogin.class));

        /*Validar respuesta vacia o nula*/
        UserLogin createdUser = response.getBody().getData();
        assertNotNull(createdUser);

        /*Valida datos insertados*/
        assertEquals(response.getBody().getData().getUserName(),userLogin.getUserName());
        assertEquals(response.getBody().getData().getUserEmail(),userLogin.getUserEmail());
        assertEquals(response.getBody().getData().getUserLogin(),userLogin.getUserLogin());

        /*Validar mensaje de respuesta*/
        assertEquals("User created successfully", response.getBody().getMessage());

    }

    @Test
    void testGetUserById() {
        /*Iniciación clase de respuesta*/
        UserLogin userLogin = new UserLogin();
        when(userService.getUserById(any(Integer.class))).thenReturn(userLogin);

        ResponseEntity<ApiResponse<UserLogin>> response = userController.getUserById(1);

        /*Validar retorno de metodo*/
        verify(userService, times(1)).getUserById(any(Integer.class));

        /*Validar respuesta vacia o nula*/
        UserLogin retrievedUser = response.getBody().getData();
        assertNotNull(retrievedUser);

        /*Validar mensaje de respuesta*/
        assertEquals("User retrieved successfully", response.getBody().getMessage());

    }

    @Test
    void testGetAllUsers() {
        /*Iniciación Lista con dos usuarios*/
        List<UserLogin> userLogins = Arrays.asList(new UserLogin(), new UserLogin());
        when(userService.getAllUsers()).thenReturn(userLogins);
        ResponseEntity<ApiResponse<List<UserLogin>>> response = userController.getAllUsers();
        verify(userService, times(1)).getAllUsers();

        /*Validar cantidad de datos retornados*/
        List<UserLogin> retrievedUsers = response.getBody().getData();
        assertNotNull(retrievedUsers);
        assertEquals(2, retrievedUsers.size());

        /*Validar mensaje de respuesta*/
        assertEquals("Users retrieved successfully", response.getBody().getMessage());

        /*Validar respuestas vacias*/
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());
        ResponseEntity<ApiResponse<List<UserLogin>>> emptyResponse = userController.getAllUsers();
        assertEquals(HttpStatus.NOT_FOUND, emptyResponse.getStatusCode());
        assertEquals("No users found", emptyResponse.getBody().getMessage());

    }

    @Test
    void testUpdateUser() {
        /*Iniciación clase de respuesta*/
        UserLogin userResponse = new UserLogin(
                1L,
                "Juan",
                "Rodriguez Cruz",
                "correo2@correo.com",
                "jrcruz",
                "123",
                true);
        when(userService.updateUser(any(Integer.class), any(UserLogin.class))).thenReturn(userResponse);
        ResponseEntity<ApiResponse<UserLogin>> response = userController.updateUser(1, userResponse);
        verify(userService, times(1)).updateUser(any(Integer.class), any(UserLogin.class));

        /*Validar  datos retornados*/
        assertTrue(response.getBody().getData().isUserState());
        assertEquals(response.getBody().getData().getUserName() , userResponse.getUserName());

        /*Validar mensaje de respuesta*/
        assertEquals("Users retrieved successfully", response.getBody().getMessage());

    }

    @Test
    void testUpdateState() {
        /*Iniciación clase de respuesta*/
        UserLogin userResponse = new UserLogin(
                1L,
                "Juan",
                "Rodriguez Cruz",
                "correo2@correo.com",
                "jrcruz",
                "123",
                true);
        when(userService.updateUserState(any(Integer.class), any(Boolean.class))).thenReturn(userResponse);
        ResponseEntity<ApiResponse<UserLogin>> response = userController.updateState(1, userResponse);
        verify(userService, times(1)).updateUserState(any(Integer.class), any(Boolean.class));
    }

    @Test
    void testLoginUser() {
        /*Iniciación clases de respuesta*/
        DataLogin dataLogin = new DataLogin("jrcruz","123");
        UserLogin userResponse = new UserLogin(
                1L,
                "Juan",
                "Rodriguez Cruz",
                "correo2@correo.com",
                "jrcruz",
                "123",
                true);
        when(userService.authenticateUser(any(DataLogin.class))).thenReturn(userResponse);
        ResponseEntity<ApiResponse<UserLogin>> response = userController.loginUser(dataLogin);
        verify(userService, times(1)).authenticateUser(any(DataLogin.class));

        /*Validar  datos retornados*/
        assertTrue(response.getBody().getData().isUserState());
        assertEquals(response.getBody().getData().getUserName() , userResponse.getUserName());

        /*Validar mensaje de respuesta*/
        assertEquals("User authenticated successfully", response.getBody().getMessage());
    }
}
