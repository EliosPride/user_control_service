package com.elios.service.impl;

import com.elios.entities.User;
import com.elios.service.AccountService;
import com.elios.validators.BirthDateValidator;
import com.elios.validators.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService
{
    final static Logger logger = LogManager.getLogger(AccountServiceImpl.class);
    private final Map<Long, User> users = new HashMap<>();
    
    @Override
    public User registerOrUpdateUser(User user)
    {
        long userId = user.getId();
        if (users.containsKey(userId))
        {
            users.put(userId, user);
            logger.trace("User with user id: " + userId + " updated.");
            return user;
        }
        else
        {
            boolean isValidEmail = EmailValidator.isValidEmail(user.getEmail());
            boolean isBirthDateValid = BirthDateValidator.isBirthDateValid(user.getDateOfBirth());
            if (isValidEmail && isBirthDateValid)
            {
                users.put(userId, user);
                logger.trace("User with user id: " + userId + ", registered.");
                return user;
            }
        }
        return null;
    }
    
    @Override
    public List<User> findAllUsers()
    {
        List<User> allUsers = new ArrayList<>();
        if (Objects.nonNull(users))
        {
            allUsers = new ArrayList<>(users.values());
            logger.trace("All users find.");
        }
        return allUsers;
    }
    
    @Override
    public User findUserById(Long id)
    {
        User user = new User();
        if (Objects.nonNull(id) && users.containsKey(id))
        {
            user = users.get(id);
            logger.trace("User with user id: " + id + " find.");
        }
        return user;
    }
    
    @Override
    public List<User> findUsersByBirthDateRange(LocalDate from, LocalDate to)
    {
        List<User> allUsers = new ArrayList<>();
        if (!users.isEmpty())
        {
            allUsers = new ArrayList<>(users.values());
        }
        
        List<User> collectedUsers = allUsers.stream()
                .filter(user -> isInDateRange(user.getDateOfBirth(), from, to))
                .collect(Collectors.toList());
        logger.trace("User by date range are collected.");
        
        return collectedUsers;
    }
    
    @Override
    public void deleteUser(Long id)
    {
        if (Objects.nonNull(id))
        {
            users.remove(id);
        }
        logger.trace("User with user id: " + id + ", deleted.");
    }
    
    private boolean isInDateRange(LocalDate date, LocalDate from, LocalDate to)
    {
        return !date.isBefore(from) && !date.isAfter(to);
    }
}
