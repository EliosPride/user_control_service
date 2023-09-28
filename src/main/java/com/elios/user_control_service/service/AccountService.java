package com.elios.user_control_service.service;

import com.elios.user_control_service.entities.User;

import java.util.List;

public interface AccountService
{
    User registerUser(User user);
    
    List<User> findAllUsers();
    
    User findUserById(Long id);
    
    User updateUser(Long id, User user);
    
    void deleteUser(Long id);
}
