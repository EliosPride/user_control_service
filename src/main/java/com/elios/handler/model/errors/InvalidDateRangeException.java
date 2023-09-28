package com.elios.handler.model.errors;

public class InvalidDateRangeException extends RuntimeException
{
    public InvalidDateRangeException(String message)
    {
        super(message);
    }
}
