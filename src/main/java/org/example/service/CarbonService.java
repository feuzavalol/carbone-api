package org.example.service;

import org.example.model.*;
import org.example.repository.*;
import org.springframework.stereotype.Service;

@Service
public class CarbonService {

    public CarbonService() {

    }

    public double getCarbonValue(Food food, double quantity){
        return food.getCost_per_unit()*quantity;
    }

    public double getCarbonValue(Transport transport, double distance){
        return transport.getCost_per_unit()*distance;
    }
}
