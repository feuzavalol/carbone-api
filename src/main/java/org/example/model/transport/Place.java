package org.example.model.transport;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Getter
    private String name;
    private double lat;
    private double lng;

    public Place() {}

    public Coord getCoords(){
        return new Coord(lat,lng);
    }
}
