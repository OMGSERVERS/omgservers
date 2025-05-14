package com.omgservers.service.factory.entity;

import com.omgservers.schema.model.entity.EntityModel;
import com.omgservers.schema.model.entity.EntityQualifierEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
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

    public EntityModel create(final EntityQualifierEnum qualifier,
                              final Long entityId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, idempotencyKey, qualifier, entityId);
    }

    public EntityModel create(final Long id,
                              final EntityQualifierEnum qualifier,
                              final Long entityId) {
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, idempotencyKey, qualifier, entityId);
    }

    public EntityModel create(final String idempotencyKey,
                              final EntityQualifierEnum qualifier,
                              final Long entityId) {
        final var id = generateIdOperation.generateId();
        return create(id, idempotencyKey, qualifier, entityId);
    }

    public EntityModel create(final Long id,
                              final String idempotencyKey,
                              final EntityQualifierEnum qualifier,
                              final Long entityId) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var entity = new EntityModel();
        entity.setId(id);
        entity.setIdempotencyKey(idempotencyKey);
        entity.setCreated(now);
        entity.setModified(now);
        entity.setQualifier(qualifier);
        entity.setEntityId(entityId);
        entity.setDeleted(Boolean.FALSE);
        return entity;
    }
}
