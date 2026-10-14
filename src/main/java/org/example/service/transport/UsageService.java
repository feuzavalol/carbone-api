package org.example.service.transport;

import org.example.model.transport.PartyCategory;
import org.example.model.transport.UsageType;
import org.example.repository.transport.UsageRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsageService {
    private final UsageRepository repository;

    public UsageService(UsageRepository repository) {
        this.repository = repository;
    }

    public boolean is_authorized(UsageType usage) {
        return !Objects.equals(usage.getName(), PartyCategory.PARTYBUS.name()) && !Objects.equals(usage.getName(), PartyCategory.PARTYCAR.name());
    }

    public UsageType getUsageType(UUID id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("No usage_type found with this uuid: "+id));
    }

    public UUID getUsageTypeId(String name){
        Optional<UsageType> usage = repository.findByName(name);
        UUID id;
        if (usage.isPresent()){
            id = usage.get().getId();
        }
        else {
            throw new DataIntegrityViolationException("UsageType not found");
        }
        return id;
    }
}
