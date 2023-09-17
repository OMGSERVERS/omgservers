package com.omgservers.module.runtime.factory;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantPermissionEnum;
import com.omgservers.operation.generateId.GenerateIdOperation;
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
                                    final Long entityId,
                                    final RuntimeGrantPermissionEnum permission) {
        final var id = generateIdOperation.generateId();
        return create(id, runtimeId, entityId, permission);
    }

    public RuntimeGrantModel create(final Long id,
                                    final Long runtimeId,
                                    final Long entityId,
                                    final RuntimeGrantPermissionEnum permission) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var runtimeGrant = new RuntimeGrantModel();
        runtimeGrant.setId(id);
        runtimeGrant.setRuntimeId(runtimeId);
        runtimeGrant.setCreated(now);
        runtimeGrant.setModified(now);
        runtimeGrant.setEntityId(entityId);
        runtimeGrant.setPermission(permission);
        return runtimeGrant;
    }
}
