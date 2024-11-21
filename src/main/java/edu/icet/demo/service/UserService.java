package edu.icet.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.icet.demo.constants.Constants;
import edu.icet.demo.constants.Status;
import edu.icet.demo.dto.UserRequest;
import edu.icet.demo.exception.UserExistsException;
import edu.icet.demo.model.Role;
import edu.icet.demo.model.User;
import edu.icet.demo.repository.RoleRepository;
import edu.icet.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<Map<String, Object>> register(UserRequest userRequest){
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent()){
            throw new UserExistsException(msgSrc.getMessage(Constants.ALREADY_EXISTS,
                    new Object[]{"user email"}, Locale.ENGLISH));
        }
        if(userRepository.findByPhoneNumber(userRequest.getPhoneNumber()).isPresent()){
            throw new UserExistsException(msgSrc.getMessage(Constants.ALREADY_EXISTS,
                    new Object[]{"user phone number"}, Locale.ENGLISH));
        }

        User newUser = mapper.convertValue(userRequest, User.class);
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setStatus(Status.NOT_VERIFIED);

        Optional<Role> role = roleRepository.findByName(edu.icet.demo.constants.Role.USER);


        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully.");
        return ResponseEntity.ok(response);
    }
}
