package com.omgservers.application.module.userModule.model.attribute;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttributeModel {

    static public AttributeModel create(final UUID player,
                                        final String name,
                                        final String value) {
        Instant now = Instant.now();

        AttributeModel attribute = new AttributeModel();
        attribute.setPlayer(player);
        attribute.setCreated(now);
        attribute.setModified(now);
        attribute.setName(name);
        attribute.setValue(value);

        return attribute;
    }

    static public void validateAttribute(AttributeModel attribute) {
        if (attribute == null) {
            throw new ServerSideBadRequestException("attribute is null");
        }
    }

    UUID player;
    @ToString.Exclude
    Instant created;
    @ToString.Exclude
    Instant modified;
    String name;
    String value;
}
