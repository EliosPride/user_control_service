package com.elios.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.Period;

public class BirthDateValidator
{
    final static Logger logger = LogManager.getLogger(BirthDateValidator.class);
    @Value("${user.age.limit}")
    private static int birthDateLimit;
    
    public static boolean isBirthDateValid(LocalDate inputDate)
    {
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(inputDate, currentDate).getYears();
    
        return age >= birthDateLimit;
    }
}
