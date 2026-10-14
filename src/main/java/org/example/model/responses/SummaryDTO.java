package org.example.model.responses;

import java.util.List;

public record SummaryDTO(List<String> labels, List<Dataset> datasets) {}
