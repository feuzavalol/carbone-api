package org.example.model.requests;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.example.model.Category;
import org.example.model.Role;

@Data
public class LoginRequest {
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Category committeeCategory;
    private int committeeNumber;
    private int year;
}
