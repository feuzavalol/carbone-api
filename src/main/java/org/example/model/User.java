package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;


@Entity
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Getter
    private String name;
    private String email;
    @Column(name="password", nullable = false)
    private String passwordHash;
    @Getter
    @Enumerated(EnumType.STRING)
    private Role role;
    private UUID committee_id;
    @Getter
    private boolean active;
    private Date created_at;
    private Date updated_at;

    public User(){}

    public User(String name, String email, String password, Role role, UUID committee_id){
        this.name = name;
        this.email = email;
        this.passwordHash = password;
        this.role = role;
        this.committee_id = committee_id;
        this.active = true;
        this.created_at = Date.valueOf(LocalDate.now());
        this.updated_at = created_at;
    }

    public record UserDTO(String username, Role role, boolean active){}

    public String getPassword() {return passwordHash;}
}
