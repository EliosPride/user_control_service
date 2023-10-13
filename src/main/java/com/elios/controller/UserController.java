package com.elios.controller;

import com.elios.entities.User;
import com.elios.handler.model.errors.*;
import com.elios.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@Validated
@RequestMapping("/users")
public class UserController
{
    final static Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private AccountService accountService;
    
    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user)
    {
        if (Objects.isNull(user))
        {
            throw new InvalidUserDataException("Invalid user data.");
        }
        User registeredUser = accountService.registerOrUpdateUser(user);
        if (Objects.isNull(registeredUser))
        {
            throw new UserRegistrationException("Failed to register user.");
        }
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/get")
    public ResponseEntity<List<User>> findAllUsers()
    {
        List<User> allUsers = accountService.findAllUsers();
        if (allUsers.isEmpty())
        {
            throw new NoUsersFoundException("No users found");
        }
        return ResponseEntity.ok(allUsers);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id)
    {
        User user = accountService.findUserById(id);
        if (Objects.isNull(user))
        {
            throw new UserNotFoundException("User with ID: " + id + ", not found.");
        }
        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user)
    {
        User existingUser = accountService.findUserById(id);
        
        if (Objects.isNull(existingUser))
        {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        
        if (Objects.isNull(user))
        {
            throw new InvalidUserDataException("Invalid user data.");
        }
        
        User updatedUser = accountService.registerOrUpdateUser(user);
        
        if (Objects.isNull(updatedUser))
        {
            throw new UserNotFoundException("User with ID: " + id + ", not found.");
        }
        return ResponseEntity.ok(updatedUser);
    }
    
    @PutMapping("/updateFields/{id}")
    public ResponseEntity<User> updateUserFields(@PathVariable Long id, @RequestBody User updatedUserData)
    {
        User existingUser = accountService.findUserById(id);
        
        if (Objects.isNull(existingUser))
        {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        
        if (Objects.nonNull(existingUser.getEmail()))
        {
            existingUser.setEmail(updatedUserData.getEmail());
        }
        if (Objects.nonNull(existingUser.getName()))
        {
            existingUser.setName(updatedUserData.getName());
        }
        if (Objects.nonNull(existingUser.getSurName()))
        {
            existingUser.setSurName(updatedUserData.getSurName());
        }
        if (Objects.nonNull(existingUser.getDateOfBirth()))
        {
            existingUser.setDateOfBirth(updatedUserData.getDateOfBirth());
        }
        if (Objects.nonNull(existingUser.getAddress()))
        {
            existingUser.setAddress(updatedUserData.getAddress());
        }
        if (Objects.nonNull(existingUser.getPhoneNumber()))
        {
            existingUser.setPhoneNumber(updatedUserData.getPhoneNumber());
        }
        
        accountService.registerOrUpdateUser(existingUser);
        
        return ResponseEntity.ok(existingUser);
    }
    
    @GetMapping("/range")
    public ResponseEntity<List<User>> findUsersByBirthDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to)
    {
        
        if (from.isAfter(to))
        {
            throw new InvalidDateRangeException("Invalid date range.");
        }
        
        List<User> usersInDateRange = accountService.findUsersByBirthDateRange(from, to);
        
        if (usersInDateRange.isEmpty())
        {
            throw new NoUsersFoundException("No users found within the date range.");
        }
        
        return ResponseEntity.ok(usersInDateRange);
    }
    
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id)
    {
        User userToDelete = accountService.findUserById(id);
        if (Objects.isNull(userToDelete))
        {
            throw new UserNotFoundException("User with ID: " + id + ", not found.");
        }
        accountService.deleteUser(id);
    }
}
