package com.omgservers.service.factory;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantTypeEnum;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeGrantModelFactory {

    final GenerateIdOperation generateIdOperation;

    public RuntimeGrantModel create(final Long runtimeId,
                                    final Long shardKey,
                                    final Long entityId,
                                    final RuntimeGrantTypeEnum type) {
        final var id = generateIdOperation.generateId();
        return create(id, runtimeId, shardKey, entityId, type);
    }

    public RuntimeGrantModel create(final Long id,
                                    final Long runtimeId,
                                    final Long shardKey,
                                    final Long entityId,
                                    final RuntimeGrantTypeEnum type) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var runtimeGrant = new RuntimeGrantModel();
        runtimeGrant.setId(id);
        runtimeGrant.setRuntimeId(runtimeId);
        runtimeGrant.setCreated(now);
        runtimeGrant.setModified(now);
        runtimeGrant.setShardKey(shardKey);
        runtimeGrant.setEntityId(entityId);
        runtimeGrant.setType(type);
        return runtimeGrant;
    }
}
