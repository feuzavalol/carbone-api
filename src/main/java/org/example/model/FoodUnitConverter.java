package org.example.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FoodUnitConverter implements AttributeConverter<FoodUnit, String> {

    @Override
    public String convertToDatabaseColumn(FoodUnit unit) {
        return unit == null ? null : unit.getDbValue();
    }

    @Override
    public FoodUnit convertToEntityAttribute(String dbValue) {
        return FoodUnit.fromDbValue(dbValue);
    }
}