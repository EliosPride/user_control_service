package com.elios.user_control_service;

import com.elios.entities.User;
import com.elios.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountServiceTest
{
    
    @InjectMocks
    private AccountServiceImpl accountService;
    
    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        Map<Long, User> users = new HashMap<>();
        
        User user1 = new User(1L, "email.bob@gmail.com", "name1", "surname1", LocalDate.of(1995, 5, 15),
                "address1", "number1");
        User user2 = new User(2L, "email.jack@gmail.com", "name2", "surname2", LocalDate.of(1996, 5, 15),
                "address2", "number2");
        User user3 = new User(3L, "email.john@gmail.com", "name3", "surname3", LocalDate.of(2001, 5, 15),
                "address3", "number3");
        users.put(user1.getId(), user1);
        users.put(user2.getId(), user2);
        users.put(user3.getId(), user3);
        
        accountService.users.putAll(users);
    }
    
    @Test
    public void testAccountService()
    {
        testRegisterOrUpdateUser();
        testFindAllUsers();
        testFindUserById();
        testFindUsersByBirthDateRange();
        testDeleteUser();
    }
    
    public void testRegisterOrUpdateUser()
    {
        User user4 = new User(4L, "example.email@gmail.com", "name4", "surname4", LocalDate.of(2004, 4, 14),
                "address4", "number4");
        
        accountService.registerOrUpdateUser(user4);
        User user = accountService.users.get(4L);
        
        assertEquals("example.email@gmail.com", user.getEmail());
        assertEquals("name4", user.getName());
        assertEquals("surname4", user.getSurName());
        assertEquals(LocalDate.of(2004, 4, 14), user.getDateOfBirth());
        assertEquals("address4", user.getAddress());
        assertEquals("number4", user.getPhoneNumber());
    }
    
    public void testFindAllUsers()
    {
        List<User> collectedUsers = accountService.findAllUsers();
        collectedUsers.sort(getUserComparator());
        
        assertEquals(4, collectedUsers.size());
        assertEquals(1L, collectedUsers.get(0).getId());
        assertEquals(2L, collectedUsers.get(1).getId());
        assertEquals(3L, collectedUsers.get(2).getId());
        assertEquals(4L, collectedUsers.get(3).getId());
    }
    
    public void testFindUserById()
    {
        Long userId = 2L;
        
        User user = accountService.findUserById(userId);
        
        assertEquals("email.jack@gmail.com", user.getEmail());
        assertEquals("name2", user.getName());
        assertEquals("surname2", user.getSurName());
        assertEquals(LocalDate.of(1996, 5, 15), user.getDateOfBirth());
        assertEquals("address2", user.getAddress());
        assertEquals("number2", user.getPhoneNumber());
    }
    
    public void testFindUsersByBirthDateRange()
    {
        LocalDate from = LocalDate.of(1990, 1, 1);
        LocalDate to = LocalDate.of(2000, 12, 31);
        
        List<User> result = accountService.findUsersByBirthDateRange(from, to);
        result.sort(getUserComparator());
        
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }
    
    public void testDeleteUser()
    {
        Long userId = 3L;
        accountService.deleteUser(userId);
        List<User> collectedUsers = new ArrayList<>(accountService.users.values());
        
        assertEquals(3, collectedUsers.size());
    }
    
    private Comparator<User> getUserComparator()
    {
        return (t1, t2) ->
        {
            Long idFirst = t1.getId();
            Long idSecond = t2.getId();
            
            return idFirst.compareTo(idSecond);
        };
    }
}
