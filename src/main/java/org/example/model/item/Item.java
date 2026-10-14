package org.example.model.item;

import java.util.UUID;

public interface Item {
    UUID getObjectId();
    double getQuantity();
    void setQuantity(double quantity);
}
