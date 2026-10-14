package org.example.model.responses;

import lombok.Getter;

import java.util.List;

public class Dataset {
    @Getter
    private String label;
    @Getter
    private List<Double> data;
    @Getter
    private List<String> backgroundColor;

    public Dataset(String label, List<Double> data, List<String> backgroundColor) {
        this.label = label;
        this.data = data;
        this.backgroundColor = backgroundColor;
    }

    public Dataset() {}
}
