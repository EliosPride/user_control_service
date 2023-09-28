package com.elios.handler.model.errors;

public class InvalidUserDataException extends RuntimeException
{
    public InvalidUserDataException(String message)
    {
        super(message);
    }
}