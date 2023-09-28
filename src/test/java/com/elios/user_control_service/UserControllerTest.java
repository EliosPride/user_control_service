package com.elios.user_control_service;

import com.elios.controller.UserController;
import com.elios.entities.User;
import com.elios.handler.CustomExceptionHandler;
import com.elios.handler.model.errors.*;
import com.elios.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest
{
    
    @InjectMocks
    private UserController userController;
    
    @Mock
    private AccountService accountService;
    
    private MockMvc mockMvc;
    
    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        
        User user1 = new User(1L, "email.bob@gmail.com", "name1", "surname1",
                LocalDate.of(1995, 5, 15), "address1", "number1");
        User user2 = new User(2L, "email.jack@gmail.com", "name2", "surname2",
                LocalDate.of(1996, 5, 15), "address2", "number2");
        User user3 = new User(3L, "email.john@gmail.com", "name3", "surname3",
                LocalDate.of(2001, 5, 15), "address3", "number3");
        accountService.registerOrUpdateUser(user1);
        accountService.registerOrUpdateUser(user2);
        accountService.registerOrUpdateUser(user3);
        
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new CustomExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> null)
                .build();
    }
    
    @Test
    public void testUserController() throws Exception
    {
        testFindUserById();
        testRegisterUser_ValidUser();
        testRegisterUser_InvalidUserData();
        testRegisterUser_FailedRegistration();
        testFindAllUsers();
        testFindAllUsers_NoUsersFound();
        testUpdateUser_ValidUser();
        testUpdateUser_InvalidUserData();
        testUpdateUser_UserNotFound();
        testFindUsersByBirthDateRange_ValidRange();
        testFindUsersByBirthDateRange_InvalidRange();
        testFindUsersByBirthDateRange_NoUsersFound();
        testDeleteUser_UserFound();
        testDeleteUser_UserNotFound();
    }
    
    public void testFindUserById() throws Exception
    {
        User expectedUser = new User(1L, "email.bob@gmail.com", "name1", "surname1",
                LocalDate.of(1995, 5, 15), "address1", "number1");
        
        when(accountService.findUserById(1L)).thenReturn(expectedUser);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("name1"))
                .andExpect(jsonPath("$.email").value("email.bob@gmail.com"));
    }
    
    public void testRegisterUser_ValidUser()
    {
        User user = new User(1L, "email.bob@gmail.com", "name1", "surname1",
                LocalDate.of(1995, 5, 15), "address1", "number1");
        
        when(accountService.registerOrUpdateUser(user)).thenReturn(user);
        
        ResponseEntity<User> response = userController.registerUser(user);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }
    
    public void testRegisterUser_InvalidUserData()
    {
        User user = null;
        
        assertThrows(InvalidUserDataException.class, () -> userController.registerUser(user));
    }
    
    public void testRegisterUser_FailedRegistration()
    {
        User user = new User(1L, "email.bob@gmail.com", "name1", "surname1",
                LocalDate.of(1995, 5, 15), "address1", "number1");
        
        when(accountService.registerOrUpdateUser(user)).thenReturn(null);
        
        assertThrows(UserRegistrationException.class, () -> userController.registerUser(user));
    }
    
    public void testFindAllUsers()
    {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "email.bob@gmail.com", "name1", "surname1",
                LocalDate.of(1995, 5, 15), "address1", "number1"));
        users.add(new User(2L, "email.jack@gmail.com", "name2", "surname2",
                LocalDate.of(1996, 5, 15), "address2", "number2"));
        
        when(accountService.findAllUsers()).thenReturn(users);
        
        ResponseEntity<List<User>> response = userController.findAllUsers();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
    }
    
    public void testFindAllUsers_NoUsersFound()
    {
        List<User> users = new ArrayList<>();
        
        when(accountService.findAllUsers()).thenReturn(users);
        
        assertThrows(NoUsersFoundException.class, () -> userController.findAllUsers());
    }
    
    public void testUpdateUser_ValidUser()
    {
        User user = new User(1L, "email.bob@gmail.com", "name1", "surname1",
                LocalDate.of(1995, 5, 15), "address1", "number1");
        
        when(accountService.registerOrUpdateUser(user)).thenReturn(user);
        
        ResponseEntity<User> response = userController.updateUser(user);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }
    
    public void testUpdateUser_InvalidUserData()
    {
        User user = null;
        
        assertThrows(InvalidUserDataException.class, () -> userController.updateUser(user));
    }
    
    public void testUpdateUser_UserNotFound()
    {
        User user = new User(1L, "email.bob@gmail.com", "name1", "surname1",
                LocalDate.of(1995, 5, 15), "address1", "number1");
        
        when(accountService.registerOrUpdateUser(user)).thenReturn(null);
        
        assertThrows(UserNotFoundException.class, () -> userController.updateUser(user));
    }
    
    public void testFindUsersByBirthDateRange_ValidRange()
    {
        LocalDate from = LocalDate.of(1990, 1, 1);
        LocalDate to = LocalDate.of(2000, 12, 31);
        List<User> usersInRange = new ArrayList<>();
        usersInRange.add(new User(1L, "email.bob@gmail.com", "name1", "surname1",
                LocalDate.of(1995, 5, 15), "address1", "number1"));
        
        when(accountService.findUsersByBirthDateRange(from, to)).thenReturn(usersInRange);
        
        ResponseEntity<List<User>> response = userController.findUsersByBirthDateRange(from, to);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usersInRange, response.getBody());
    }
    
    public void testFindUsersByBirthDateRange_InvalidRange()
    {
        LocalDate from = LocalDate.of(2000, 1, 1);
        LocalDate to = LocalDate.of(1990, 12, 31);
        
        assertThrows(InvalidDateRangeException.class, () -> userController.findUsersByBirthDateRange(from, to));
    }
    
    public void testFindUsersByBirthDateRange_NoUsersFound()
    {
        LocalDate from = LocalDate.of(1990, 1, 1);
        LocalDate to = LocalDate.of(2000, 12, 31);
        
        when(accountService.findUsersByBirthDateRange(from, to)).thenReturn(new ArrayList<>());
        
        assertThrows(NoUsersFoundException.class, () -> userController.findUsersByBirthDateRange(from, to));
    }
    
    public void testDeleteUser_UserFound()
    {
        Long userId = 1L;
        User userToDelete = new User(1L, "email.bob@gmail.com", "name1", "surname1",
                LocalDate.of(1995, 5, 15), "address1", "number1");
        
        when(accountService.findUserById(userId)).thenReturn(userToDelete);
        
        userController.deleteUser(userId);
        
        verify(accountService, times(1)).deleteUser(userId);
    }
    
    public void testDeleteUser_UserNotFound()
    {
        Long userId = 1L;
        
        when(accountService.findUserById(userId)).thenReturn(null);
        
        assertThrows(UserNotFoundException.class, () -> userController.deleteUser(userId));
    }
}


