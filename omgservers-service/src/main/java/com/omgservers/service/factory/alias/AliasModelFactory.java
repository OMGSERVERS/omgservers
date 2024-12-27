package com.omgservers.service.factory.alias;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.alias.AliasQualifierEnum;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class AliasModelFactory {

    final GenerateIdOperation generateIdOperation;

    public AliasModel create(final AliasQualifierEnum qualifier,
                             final Long shardKey,
                             final Long uniquenessGroup,
                             final String aliasValue) {
        final var id = generateIdOperation.generateId();
        final var entityId = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, qualifier, shardKey, uniquenessGroup, entityId, aliasValue, idempotencyKey);
    }

    public AliasModel create(final AliasQualifierEnum qualifier,
                             final Long shardKey,
                             final Long uniquenessGroup,
                             final Long entityId,
                             final String aliasValue) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, qualifier, shardKey, uniquenessGroup, entityId, aliasValue, idempotencyKey);
    }

    public AliasModel create(final AliasQualifierEnum qualifier,
                             final Long shardKey,
                             final Long uniquenessGroup,
                             final Long entityId,
                             final String aliasValue,
                             final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, qualifier, shardKey, uniquenessGroup, entityId, aliasValue, idempotencyKey);
    }

    public AliasModel create(final Long id,
                             final AliasQualifierEnum qualifier,
                             final Long shardKey,
                             final Long uniquenessGroup,
                             final Long entityId,
                             final String value,
                             final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var alias = new AliasModel();
        alias.setId(id);
        alias.setIdempotencyKey(idempotencyKey);
        alias.setCreated(now);
        alias.setModified(now);
        alias.setQualifier(qualifier);
        alias.setShardKey(shardKey);
        alias.setUniquenessGroup(uniquenessGroup);
        alias.setEntityId(entityId);
        alias.setValue(value);
        alias.setDeleted(false);

        return alias;
    }
}
