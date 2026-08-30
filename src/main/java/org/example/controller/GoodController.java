package org.example.controller;

import org.example.model.Good;
import org.example.model.responses.CarbonResponse;
import org.example.service.GoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class GoodController {
    private final GoodService service;

    public GoodController(GoodService service) {
        this.service = service;
    }

    @GetMapping("/singleGood")
    public Good findGood(@RequestParam UUID goodId){
        return service.getGood(goodId);
    }

    @GetMapping("/good")
    public List<Good> findAllGood() {return service.getGoodList();}

    @GetMapping("/goodCarbon")
    public ResponseEntity<CarbonResponse> findCarbonValue(
            @RequestParam UUID goodId,
            @RequestParam double quantity
    ){
        CarbonResponse response = service.getCarbonValue(goodId,quantity);
        return ResponseEntity.ok(response);
    }
}
