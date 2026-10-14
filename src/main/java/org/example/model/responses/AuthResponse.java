package org.example.model.responses;

public record AuthResponse(boolean success, String message, String token) {}
