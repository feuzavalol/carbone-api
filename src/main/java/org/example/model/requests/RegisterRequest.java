package org.example.model.requests;

import org.example.model.Category;
import org.example.model.Role;

public record RegisterRequest(String username, String email, String password,
                              Role role, Category committeeCategory, int committeeNumber,
                              int year) {}