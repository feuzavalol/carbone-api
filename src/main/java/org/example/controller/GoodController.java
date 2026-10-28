package org.example.controller;

import org.example.model.ExpectedUnit;
import org.example.model.Good;
import org.example.model.GoodCategory;
import org.example.model.requests.good.ModifyGoodRequest;
import org.example.model.responses.CarbonResponse;
import org.example.security.AuthenticatedUser;
import org.example.security.SecurityUtil;
import org.example.service.GoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @GetMapping("/goodCategories")
    public ResponseEntity<Map<String,List<String>>> findAllCategory(){
        List<String> categories = Stream.of(GoodCategory.values())
                                    .map(Enum::name)
                                    .toList();

        Map<String, List<String>> success = Map.of("success",categories);
        return ResponseEntity.ok(success);
    }

    @GetMapping("/expectedUnits")
    public ResponseEntity<Map<String,List<String>>> findAllExpectedUnits(){
        List<String> expectedUnits = ExpectedUnit.allValues();

        Map<String, List<String>> success = Map.of("success",expectedUnits);
        return ResponseEntity.ok(success);
    }

    @PostMapping("/modifyGood")
    public ResponseEntity<Map<String,Good>> modifyGood(
            @RequestBody ModifyGoodRequest request,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ){
        if (SecurityUtil.isUserAdmin(currentUser)){
            Good result = service.modifyGood(request.getGood());
            Map<String,Good> success;
            success = Map.of("success",result);
            return ResponseEntity.ok(success);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("Erreur : Un ou plusieurs auteurs de modifications ont posé problème", new Good()));
    }

    @PostMapping("/createGood")
    public ResponseEntity<Map<String,Good>> createGood(
            @RequestBody ModifyGoodRequest request,
            @AuthenticationPrincipal AuthenticatedUser currentUser
    ){
        if (SecurityUtil.isUserAdmin(currentUser)){
            System.out.println(request.getGood());
            Good result = service.createGood(request.getGood());
            Map<String,Good> success;
            success = Map.of("success",result);
            return ResponseEntity.ok(success);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("Erreur : Un ou plusieurs auteurs de modifications ont posé problème", new Good()));
    }
}
