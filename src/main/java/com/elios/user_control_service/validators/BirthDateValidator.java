package com.elios.user_control_service.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.Date;

public class BirthDateValidator
{
    final static Logger logger = LogManager.getLogger(BirthDateValidator.class);
    
    public static boolean isBirthDateValid(Date inputDate)
    {
        Calendar currentDate = Calendar.getInstance();
        
        return inputDate.before(currentDate.getTime());
    }
}
