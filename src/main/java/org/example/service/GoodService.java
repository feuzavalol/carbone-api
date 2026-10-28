package org.example.service;

import org.example.model.*;
import org.example.model.responses.CarbonResponse;
import org.example.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GoodService {

    private final GoodRepo goodRepo;
    private final CarbonService carbonService;

    public GoodService(GoodRepo goodRepo, CarbonService carbonService) {

        this.goodRepo = goodRepo;
        this.carbonService = carbonService;
    }

    public boolean existsById(UUID id){
        return goodRepo.existsById(id);
    }

    public List<Good> getGoodList() {return goodRepo.findAll();}

    public Good getGood(UUID goodId) {
        return goodRepo.findById(goodId)
                .orElseThrow(() -> new RuntimeException("There is no good matching this uuid: "+goodId));
    }

    public CarbonResponse getCarbonValue(UUID goodId, double quantity) {
        Good good = getGood(goodId);

        return new CarbonResponse(carbonService.getCarbonValue(good,quantity));
    }

    public double getCostPerUnit(UUID objectId) {
        Good good = getGood(objectId);
        return good.getCostPerUnit();
    }

    public String getName(UUID objectId) {
        Good good = getGood(objectId);
        return good.getName();
    }

    public Good modifyGood(Good good) {
        if (existsById(good.getId())) {
            return goodRepo.save(good);
        }
        else {
            throw new RuntimeException("There is no good matching this uuid: "+good.getId()+"\nHere is the good in question: "+good.toString());
        }
    }

    public Good createGood(Good good) {
        if (good.getId() == null) {
            return goodRepo.save(good);
        }
        else {
            throw new RuntimeException("This item already exists and shouldn't modify through this way: "+good.toString());
        }
    }
}
