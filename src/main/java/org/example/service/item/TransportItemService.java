package org.example.service.item;

import org.example.model.Transport;
import org.example.model.User;
import org.example.model.item.TransportItem;
import org.example.model.requests.TransportRequest;
import org.example.model.responses.TransportRowDTO;
import org.example.repository.*;
import org.example.repository.item.TransportItemRepo;
import org.example.service.CarbonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TransportItemService {

    private final TransportItemRepo repository;
    private final TransportRepo transportRepo;
    private final UserRepo userRepo;
    private final CarbonService carbonService;

    public TransportItemService(TransportItemRepo repository, TransportRepo transportRepo, UserRepo userRepo, CarbonService carbonService) {
        this.repository = repository;
        this.transportRepo = transportRepo;
        this.userRepo = userRepo;
        this.carbonService = carbonService;
    }

    public List<TransportRowDTO> getTransportItemList(UUID committeeID) {
        List<TransportItem> transportItems = repository.findByAuthorId(committeeID)
                .orElse(new java.util.ArrayList<>(List.of()));
        List<TransportRowDTO> result = new java.util.ArrayList<>(List.of());
        for (TransportItem t : transportItems) {
            Transport temp = transportRepo.findById(t.getObjectId()).orElseThrow(() -> new RuntimeException("Couldn't find any transport for this uuid: " + t.getObjectId()));
            double carbonValue = carbonService.getCarbonValue(temp, t.getDistance());
            result.add(new TransportRowDTO(temp.getName(), t.getDistance(), carbonValue));
        }
        return result;
    }

    public TransportItem createTransportItem(TransportRequest request) {
        Transport transport = transportRepo
                .findByName(request.getTransportName())
                .orElseThrow(() -> new RuntimeException("Transport not found or too many transport found: " + request.getTransportName() ));

        User author = userRepo
                .findByName(request.getAuthorName())
                .orElseThrow(() -> new RuntimeException("Author for this request not found: " + request.getAuthorName()));

        return new TransportItem(author.getId(),"TRANSPORT", transport.getId(), request.getValue());
    }

    @Transactional
    public void createAndSaveTransportItem(TransportRequest request) {
        TransportItem transportItem = createTransportItem(request);
        repository.save(transportItem);
    }
}
