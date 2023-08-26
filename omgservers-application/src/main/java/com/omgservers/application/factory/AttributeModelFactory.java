package com.omgservers.application.factory;

import com.omgservers.base.operation.generateId.GenerateIdOperation;
import com.omgservers.model.attribute.AttributeModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class AttributeModelFactory {

    final GenerateIdOperation generateIdOperation;

    public AttributeModel create(final Long playerId,
                                 final String name,
                                 final String value) {
        final var id = generateIdOperation.generateId();
        return create(id, playerId, name, value);
    }

    public AttributeModel create(final Long id,
                                 final Long playerId,
                                 final String name,
                                 final String value) {
        Instant now = Instant.now();

        AttributeModel attribute = new AttributeModel();
        attribute.setId(id);
        attribute.setPlayerId(playerId);
        attribute.setCreated(now);
        attribute.setModified(now);
        attribute.setName(name);
        attribute.setValue(value);

        return attribute;
    }
}
