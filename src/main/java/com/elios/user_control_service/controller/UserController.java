package com.elios.user_control_service.controller;

import com.elios.user_control_service.entities.User;
import com.elios.user_control_service.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    final static Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private AccountService accountService;
    @Value("${user.age.limit}")
    private Integer ageLimit;
    
    public User registerUser()
    {
        return null;
    }
    
    public List<User> findAllUsers()
    {
        return null;
    }
    
    public User findUserById()
    {
        return null;
    }
    
    public User updateUser()
    {
        return null;
    }
    
    public void deleteUser()
    {
    
    }
}
