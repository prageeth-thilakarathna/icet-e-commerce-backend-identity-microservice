package edu.icet.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.constants.Constants;
import edu.icet.demo.constants.Status;
import edu.icet.demo.dto.LoginRequest;
import edu.icet.demo.dto.LoginResponse;
import edu.icet.demo.dto.ResponseDTO;
import edu.icet.demo.dto.UserRequest;
import edu.icet.demo.exception.UnauthorizedException;
import edu.icet.demo.exception.UserExistsException;
import edu.icet.demo.model.Role;
import edu.icet.demo.model.User;
import edu.icet.demo.repository.RoleRepository;
import edu.icet.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MessageSource msgSrc;
    private final ObjectMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public ResponseEntity<Map<String, Object>> register(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new UserExistsException(msgSrc.getMessage(Constants.ALREADY_EXISTS,
                    new Object[]{"user email"}, Locale.ENGLISH));
        }
        if (userRepository.findByPhoneNumber(userRequest.getPhoneNumber()).isPresent()) {
            throw new UserExistsException(msgSrc.getMessage(Constants.ALREADY_EXISTS,
                    new Object[]{"user phone number"}, Locale.ENGLISH));
        }

        User newUser = mapper.convertValue(userRequest, User.class);
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setStatus(Status.NOT_VERIFIED);

        Optional<Role> role = roleRepository.findByName(edu.icet.demo.constants.Role.USER);
        newUser.setRole(role.orElse(null));

        userRepository.save(newUser);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "The user registered successfully.");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ResponseDTO> authenticate(String token) {
        return ResponseEntity.ok(ResponseDTO.builder()
                .status(HttpStatus.OK).message("The token is valid.").build());
    }

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.email());
        if (user.isEmpty()) {
            throw new UnauthorizedException(
                    msgSrc.getMessage("exception.unauthorized.invalid_credentials",
                            null, Locale.ENGLISH));
        }
        if(user.get().getStatus()!=Status.ACTIVE){
            throw new UnauthorizedException(
                    msgSrc.getMessage("exception.unauthorized.account_not_active",
                            null, Locale.ENGLISH));
        }

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.get().getEmail(), loginRequest.password()));
        } catch (AuthenticationException e){
            throw new UnauthorizedException(
                    msgSrc.getMessage("exception.unauthorized.invalid_credentials",
                            null, Locale.ENGLISH));
        }

        return null;

    }
}