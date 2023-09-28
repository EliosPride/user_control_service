package com.elios.handler.model.errors;

public class NoUsersFoundException extends RuntimeException
{
    public NoUsersFoundException(String message)
    {
        super(message);
    }
}
