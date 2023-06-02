package com.incofer.demo.exceptions;

public class MyCustomException extends RuntimeException
{
    public MyCustomException (final String message)
    {
        super(message);
    }
}
