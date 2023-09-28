package com.elios.service;

import com.elios.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface AccountService
{
    User registerOrUpdateUser(User user);
    
    List<User> findAllUsers();
    
    User findUserById(Long id);
    
    List<User> findUsersByBirthDateRange(LocalDate from, LocalDate to);
    
    void deleteUser(Long id);
}
