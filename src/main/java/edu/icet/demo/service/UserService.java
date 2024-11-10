package edu.icet.demo.service;

import edu.icet.demo.dto.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    public ResponseEntity<Map<String, Object>> register(UserRequest userRequest){
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully.");
        return ResponseEntity.ok(response);
    }
}
