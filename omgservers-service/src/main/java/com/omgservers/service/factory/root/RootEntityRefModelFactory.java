package com.omgservers.service.factory.root;

import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import com.omgservers.schema.model.rootEntityRef.RootEntityRefQualifierEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RootEntityRefModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RootEntityRefModel create(final Long rootId,
                                     final RootEntityRefQualifierEnum qualifier,
                                     final Long entityId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();

        return create(id, idempotencyKey, rootId, qualifier, entityId);
    }

    public RootEntityRefModel create(final Long id,
                                     final Long rootId,
                                     final RootEntityRefQualifierEnum qualifier,
                                     final Long entityId) {
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, idempotencyKey, rootId, qualifier, entityId);
    }

    public RootEntityRefModel create(final String idempotencyKey,
                                     final Long rootId,
                                     final RootEntityRefQualifierEnum qualifier,
                                     final Long entityId) {
        final var id = generateIdOperation.generateId();
        return create(id, idempotencyKey, rootId, qualifier, entityId);
    }

    public RootEntityRefModel create(final Long id,
                                     final String idempotencyKey,
                                     final Long rootId,
                                     final RootEntityRefQualifierEnum qualifier,
                                     final Long entityId) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var rootEntityRef = new RootEntityRefModel();
        rootEntityRef.setId(id);
        rootEntityRef.setIdempotencyKey(idempotencyKey);
        rootEntityRef.setRootId(rootId);
        rootEntityRef.setCreated(now);
        rootEntityRef.setModified(now);
        rootEntityRef.setQualifier(qualifier);
        rootEntityRef.setEntityId(entityId);
        rootEntityRef.setDeleted(Boolean.FALSE);
        return rootEntityRef;
    }
}
