package org.example.controller;

import org.example.model.responses.CommitteeDTO;
import org.example.service.CommitteeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
