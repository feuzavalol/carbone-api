package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;
import java.util.UUID;

@Entity
public class Committee {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Getter
    @Enumerated(EnumType.STRING)
    private CommitteeCategory category;
    @Getter
    private Integer number;
    @Getter
    @Column(name="active_year")
    private int year;
    private boolean active;
    private Date created_at;

    public boolean getActive() {return active;}
    public Date getCreationDate() {return created_at;}
}
