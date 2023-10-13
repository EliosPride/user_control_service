package com.elios.entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

@Data
public class User
{
    final static Logger logger = LogManager.getLogger(User.class);
    
    private Long id;
    @Email
    private String email;
    private String name;
    private String surName;
    @Past
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
    
    public User()
    {
    }
    
    public User(Long id, String email, String name, String surName, LocalDate dateOfBirth, String address, String phoneNumber)
    {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surName = surName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
