package org.example.model.requests;

import org.example.model.CommitteeCategory;
import org.example.model.Role;

public record RegisterRequest(String username, String email, String password,
                              Role role, CommitteeCategory committeeCategory, int committeeNumber,
                              int year) {}