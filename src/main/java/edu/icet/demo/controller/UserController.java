package edu.icet.demo.controller;

import edu.icet.demo.constants.Constants;
import edu.icet.demo.dto.LoginRequest;
import edu.icet.demo.dto.LoginResponse;
import edu.icet.demo.dto.ResponseDTO;
import edu.icet.demo.dto.UserRequest;
import edu.icet.demo.exception.InvalidParameterException;
import edu.icet.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MessageSource msgSrc;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(
            @Valid @RequestBody UserRequest userRequest, BindingResult result) {
        if (result.hasErrors()) {
            FieldError error = result.getFieldError();
            throw new InvalidParameterException(msgSrc.getMessage(Constants.FIELD_ISSUE,
                    new Object[]{Objects.requireNonNull(error).getField(),
                            error.getDefaultMessage()}, Locale.ENGLISH));
        }
        return userService.register(userRequest);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<ResponseDTO> authenticate(@RequestParam String token) {
        return userService.authenticate(token);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
}
