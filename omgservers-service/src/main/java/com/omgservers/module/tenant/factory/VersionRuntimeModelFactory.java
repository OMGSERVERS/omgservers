package com.omgservers.module.tenant.factory;

import com.omgservers.model.versionRuntime.VersionRuntimeModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionRuntimeModelFactory {

    final GenerateIdOperation generateIdOperation;

    public VersionRuntimeModel create(final Long tenantId,
                                      final Long versionId,
                                      final Long runtimeId) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, versionId, runtimeId);
    }

    public VersionRuntimeModel create(final Long id,
                                      final Long tenantId,
                                      final Long versionId,
                                      final Long runtimeId) {
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var model = new VersionRuntimeModel();
        model.setId(id);
        model.setTenantId(tenantId);
        model.setVersionId(versionId);
        model.setCreated(now);
        model.setModified(now);
        model.setRuntimeId(runtimeId);
        model.setDeleted(false);
        return model;
    }
}
