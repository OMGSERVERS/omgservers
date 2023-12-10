package com.omgservers.service.factory;

import com.omgservers.model.entitiy.EntityModel;
import com.omgservers.model.entitiy.EntityQualifierEnum;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class EntityModelFactory {

    final GenerateIdOperation generateIdOperation;

    public EntityModel create(final Long entityId,
                              final EntityQualifierEnum qualifier) {
        final var id = generateIdOperation.generateId();
        return create(id, entityId, qualifier);
    }

    public EntityModel create(final Long id,
                              final Long entityId,
                              final EntityQualifierEnum qualifier) {
        var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var entity = new EntityModel();
        entity.setId(id);
        entity.setCreated(now);
        entity.setModified(now);
        entity.setEntityId(entityId);
        entity.setQualifier(qualifier);
        entity.setDeleted(false);
        return entity;
    }
}
