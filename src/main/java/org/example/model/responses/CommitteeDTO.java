package org.example.model.responses;

import org.example.model.CommitteeCategory;

import java.util.UUID;

public record CommitteeDTO(UUID id, CommitteeCategory category, Integer number, int year){}
