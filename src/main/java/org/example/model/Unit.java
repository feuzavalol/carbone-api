package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.List;

public enum Unit {
    KG_CO2E_PER_KG("kgCO2eq/kg"),
    KG_CO2E_PER_UNIT("kgCO2eq/unités" ),
    KG_CO2E_PER_L("kgCO2eq/L");

    private final String dbValue;

    Unit(String dbValue) {
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
    public static Unit fromDbValue(String value) {
        if (value == null) return null;
        for (Unit unit : values()) {
            if (unit.dbValue.equals(value)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Unknown unit: " + value);
    }

    public static List<String> allValues() {
        return Arrays.stream(values())
                .map(Unit::getDbValue)
                .toList();
    }
}
