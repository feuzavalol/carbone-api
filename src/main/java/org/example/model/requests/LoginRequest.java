package org.example.model.request;

import lombok.Data;
import org.example.model.Category;

@Data
public class LoginRequest {
    private String username;
    private String email;
    private String password;
    private Category committee_category;
    private int committee_number;
}
