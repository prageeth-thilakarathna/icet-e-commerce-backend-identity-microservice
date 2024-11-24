package edu.icet.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR,
        reason = "The system internal errors.")
public class InternalServerException extends RuntimeException {

    public InternalServerException(String message) {
        super(message);
    }
}
