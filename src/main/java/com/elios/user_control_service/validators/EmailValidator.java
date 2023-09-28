package com.elios.user_control_service.validators;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator
{
    final static Logger logger = LogManager.getLogger(EmailValidator.class);
    
    public static boolean isValidEmail(String email)
    {
        String pattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        
        Pattern regexPattern = Pattern.compile(pattern);
        
        Matcher matcher = regexPattern.matcher(email);
        
        return matcher.matches();
    }
}
