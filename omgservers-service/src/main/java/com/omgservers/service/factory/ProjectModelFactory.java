package com.omgservers.service.factory;

import com.omgservers.model.project.ProjectModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ProjectModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ProjectModel create(final Long tenantId) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = UUID.randomUUID().toString();
        return create(id, tenantId, idempotencyKey);
    }

    public ProjectModel create(final Long tenantId, final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, idempotencyKey);
    }

    public ProjectModel create(final Long id,
                               final Long tenantId,
                               final String idempotencyKey) {
        var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        ProjectModel project = new ProjectModel();
        project.setId(id);
        project.setTenantId(tenantId);
        project.setCreated(now);
        project.setModified(now);
        project.setIdempotencyKey(idempotencyKey);
        project.setDeleted(false);
        return project;
    }
}
