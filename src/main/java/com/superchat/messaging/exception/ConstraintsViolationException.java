package com.superchat.messaging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ConstraintsViolationException extends Exception
{

    public ConstraintsViolationException(String message)
    {
        super(message);
    }


    public ConstraintsViolationException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
