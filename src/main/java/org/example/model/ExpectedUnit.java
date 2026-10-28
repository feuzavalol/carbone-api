package org.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.List;

public enum ExpectedUnit {
    KG("kg"),
    UNIT("unités" ),
    L("L");

    private final String dbValue;

    ExpectedUnit(String dbValue) {
        this.dbValue = dbValue;
    }

    @JsonValue
    public String getDbValue() {
        return dbValue;
    }

    @Override
    public String toString() {
        return getDbValue();
    }

    @JsonCreator
    public static ExpectedUnit fromDbValue(String value) {
        if (value == null) return null;
        for (ExpectedUnit unit : values()) {
            if (unit.dbValue.equals(value)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Unknown unit: " + value);
    }

    public static List<String> allValues() {
        return Arrays.stream(values())
                .map(ExpectedUnit::getDbValue)
                .toList();
    }
}
