package org.example.controller;

import org.example.model.responses.CommitteeDTO;
import org.example.service.CommitteeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class CommitteeController {
    private final CommitteeService service;

    public CommitteeController(CommitteeService service) {

        this.service = service;
    }

    @GetMapping("/committee")
    public ResponseEntity<CommitteeDTO> findCommittee(
            @RequestParam UUID id
    ){
        CommitteeDTO response = service.getCommittee(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/committees")
    public ResponseEntity<Map<Integer,List<CommitteeDTO>>> findAllCommitteeByYear(
            @RequestParam int year
    ){
        List<CommitteeDTO> result = service.getAllCommitteeByYear(year);

        Map<Integer, List<CommitteeDTO>> success = Map.of(year,result);
        return ResponseEntity.ok(success);
    }
}
