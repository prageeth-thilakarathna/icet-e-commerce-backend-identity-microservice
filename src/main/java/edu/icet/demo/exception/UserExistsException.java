package edu.icet.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "The user already exists.")
public class UserExistsException extends RuntimeException {

    public UserExistsException(String message) {
        super(message);
    }
}
