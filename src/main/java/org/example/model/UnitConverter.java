package org.example.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UnitConverter implements AttributeConverter<Unit, String> {

    @Override
    public String convertToDatabaseColumn(Unit unit) {
        return unit == null ? null : unit.getDbValue();
    }

    @Override
    public Unit convertToEntityAttribute(String dbValue) {
        return Unit.fromDbValue(dbValue);
    }
}