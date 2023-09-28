package com.elios.handler.model.errors;

public class UserRegistrationException extends RuntimeException
{
    public UserRegistrationException(String message)
    {
        super(message);
    }
}
