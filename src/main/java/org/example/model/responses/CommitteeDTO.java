package org.example.model.responses;

import org.example.model.Category;

public record CommitteeDTO(Category category, int number, int year){}
