package org.example.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ExceptedUnitConverter implements AttributeConverter<ExpectedUnit, String> {
    @Override
    public String convertToDatabaseColumn(ExpectedUnit unit) {
        return unit == null ? null : unit.getDbValue();
    }

    @Override
    public ExpectedUnit convertToEntityAttribute(String dbValue) {
        return ExpectedUnit.fromDbValue(dbValue);
    }
}
