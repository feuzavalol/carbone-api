package org.example.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.UUID;

@Entity
public class Committee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Category category;
    private int number;
    private int year;
    private boolean active;
    private Date created_at;

    public UUID getId() {return id;}
    public Category getCategory() {return category;}
    public int getNumber() {return number;}
    public int getYear() {return year;}
    public boolean getActive() {return active;}
    public Date getCreationDate() {return created_at;}
}
