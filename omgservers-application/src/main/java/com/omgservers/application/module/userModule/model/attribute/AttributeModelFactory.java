package com.omgservers.application.module.userModule.model.attribute;

import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
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