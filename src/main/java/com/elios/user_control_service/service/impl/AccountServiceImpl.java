package com.elios.user_control_service.service.impl;

import com.elios.user_control_service.entities.User;
import com.elios.user_control_service.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService
{
    final static Logger logger = LogManager.getLogger(AccountServiceImpl.class);
    
    @Override
    public User registerUser(User user)
    {
        return null;
    }
    
    @Override
    public List<User> findAllUsers()
    {
        return null;
    }
    
    @Override
    public User findUserById(Long id)
    {
        return null;
    }
    
    @Override
    public User updateUser(Long id, User user)
    {
        return null;
    }
    
    @Override
    public void deleteUser(Long id)
    {
    
    }
}
