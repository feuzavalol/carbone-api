package org.example.model.categories;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Entity
public class MealType {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Getter
    private String name;
    @Column(name="is_liste", nullable = false)
    private boolean isListe;

    public boolean getIsListe() {
        return isListe;
    }
}
