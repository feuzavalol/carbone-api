package org.example.security;

import org.example.model.Role;

import java.util.UUID;

public record AuthenticatedUser(UUID id, String email, UUID committeeId, Role role) {}
