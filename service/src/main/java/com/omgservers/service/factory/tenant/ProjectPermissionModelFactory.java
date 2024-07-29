package com.omgservers.service.factory.tenant;

import com.omgservers.schema.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.schema.model.projectPermission.ProjectPermissionModel;
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
public class ProjectPermissionModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ProjectPermissionModel create(final Long tenantId,
                                         final Long projectId,
                                         final Long userId,
                                         final ProjectPermissionEnum permission) {
        final var id = generateIdOperation.generateId();
        final var idempotencyKey = generateIdOperation.generateStringId();
        return create(id, tenantId, projectId, userId, permission, idempotencyKey);
    }

    public ProjectPermissionModel create(final Long tenantId,
                                         final Long projectId,
                                         final Long userId,
                                         final ProjectPermissionEnum permission,
                                         final String idempotencyKey) {
        final var id = generateIdOperation.generateId();
        return create(id, tenantId, projectId, userId, permission, idempotencyKey);
    }

    public ProjectPermissionModel create(final Long id,
                                         final Long tenantId,
                                         final Long projectId,
                                         final Long userId,
                                         final ProjectPermissionEnum permission,
                                         final String idempotencyKey) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var projectPermission = new ProjectPermissionModel();
        projectPermission.setId(id);
        projectPermission.setTenantId(tenantId);
        projectPermission.setProjectId(projectId);
        projectPermission.setCreated(now);
        projectPermission.setModified(now);
        projectPermission.setIdempotencyKey(idempotencyKey);
        projectPermission.setUserId(userId);
        projectPermission.setPermission(permission);
        projectPermission.setDeleted(false);
        return projectPermission;
    }
}
