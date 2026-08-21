package org.example.model.responses;

import org.example.model.Category;

import java.util.UUID;

public record CommitteeDTO(UUID id, Category category, int number, int year){}
