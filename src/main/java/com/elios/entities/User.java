package com.elios.entities;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;

@Data
public class User
{
    final static Logger logger = LogManager.getLogger(User.class);
    
    private Long id;
    private String email;
    private String name;
    private String surName;
    private LocalDate dateOfBirth;
    private String address;
    private String phoneNumber;
}
