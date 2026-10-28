package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.List;

public enum FoodUnit {
    KG_CO2E_PER_KG("kgCO2e/kg"),
    KG_CO2E_PER_UNIT("kgCO2e/unités" ),
    KG_CO2E_PER_L("kgCO2e/L");

    private final String dbValue;

    FoodUnit(String dbValue) {
        this.dbValue = dbValue;
    }

    @JsonValue
    public String getDbValue() {
        return dbValue;
    }

    @Override
    public String toString() {
        return dbValue;
    }

    @JsonCreator
    public static FoodUnit fromDbValue(String value) {
        if (value == null) return null;
        for (FoodUnit unit : values()) {
            if (unit.dbValue.equals(value)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Unknown unit: " + value);
    }

    public static List<String> allValues() {
        return Arrays.stream(values())
                .map(FoodUnit::getDbValue)
                .toList();
    }
}
