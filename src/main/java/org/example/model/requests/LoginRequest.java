package org.example.model.requests;

import lombok.Data;
import org.example.model.Category;
import org.example.model.Role;

@Data
public class LoginRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
    private Category committeeCategory;
    private int committeeNumber;
}
