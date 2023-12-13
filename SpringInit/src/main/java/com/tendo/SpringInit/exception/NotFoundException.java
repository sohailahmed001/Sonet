package com.tendo.SpringInit.exception;

public class NotFoundException extends RuntimeException
{
    public NotFoundException(Class<?> className)
    {
        super(className.getSimpleName() + " not found !!");
    }
}
