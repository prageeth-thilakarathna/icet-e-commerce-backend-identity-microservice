package edu.icet.demo.dto;

public record LoginRequest(
        String email,
        String password
) {
}
