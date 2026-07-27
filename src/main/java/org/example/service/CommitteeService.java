package org.example.service;

import org.example.model.*;
import org.example.model.responses.CommitteeDTO;
import org.example.repository.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommitteeService {

    private final CommitteeRepo repo;
    private final CarbonService carbonService;

    public CommitteeService(CommitteeRepo repo, CarbonService carbonService) {

        this.repo = repo;
        this.carbonService = carbonService;
    }

    public CommitteeDTO getCommittee(UUID id) {
        Committee committee = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Committee not found for id:" + id));
        return new CommitteeDTO(committee.getCategory(),committee.getNumber(),committee.getYear());
    }

}
