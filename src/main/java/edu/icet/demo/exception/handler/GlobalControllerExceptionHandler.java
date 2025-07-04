package edu.icet.demo.exception.handler;

import edu.icet.demo.exception.InternalServerException;
import edu.icet.demo.exception.InvalidParameterException;
import edu.icet.demo.exception.UnauthorizedException;
import edu.icet.demo.exception.UserExistsException;
import edu.icet.demo.exception.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(InvalidParameterException.class)
    protected ResponseEntity<ErrorResponse> handleBadRequest(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .status(HttpStatus.BAD_REQUEST).message(e.getMessage()).build());
    }

    @ExceptionHandler(UserExistsException.class)
    protected ResponseEntity<ErrorResponse> handleConflict(Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .status(HttpStatus.CONFLICT).message(e.getMessage()).build());
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ErrorResponse> handleUnauthorizedRequest(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED).message(e.getMessage()).build());
    }

    @ExceptionHandler(InternalServerException.class)
    protected ResponseEntity<ErrorResponse> handleInternalServerError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR).message(e.getMessage()).build());
    }
}
