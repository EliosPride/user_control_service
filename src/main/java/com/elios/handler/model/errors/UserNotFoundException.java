package com.elios.handler.model.errors;

public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(String message)
    {
        super(message);
    }
}