package com.example.warehouse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class JobNotFoundException extends RuntimeException{
    public JobNotFoundException() {
        super();
    }
    public JobNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public JobNotFoundException(String message) {
        super(message);
    }
    public JobNotFoundException(Throwable cause) {
        super(cause);
    }
}
