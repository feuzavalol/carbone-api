package org.example.service;

import org.example.model.*;
import org.example.model.responses.CommitteeDTO;
import org.example.repository.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        return new CommitteeDTO(committee.getId(),committee.getCategory(),committee.getNumber(),committee.getYear());
    }

    private List<CommitteeDTO> convertToDTO(List<Committee> committeeList){
        List<CommitteeDTO> result = new ArrayList<>();
        for (Committee committee : committeeList){
            result.add(new CommitteeDTO(committee.getId(),committee.getCategory(),committee.getNumber(),committee.getYear()));
        }
        return result;
    }

    public List<CommitteeDTO> getAllCommitteeByYear(int year){
        List<Committee> committeeList = repo.findByYear(year)
                .orElseThrow(() -> new RuntimeException("Committees not found for year:" + year));

        return convertToDTO(committeeList);
    }
}
