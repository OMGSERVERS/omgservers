package com.omgservers.service.factory;

import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

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
        return create(id, tenantId, projectId, userId, permission);
    }

    public ProjectPermissionModel create(final Long id,
                                         final Long tenantId,
                                         final Long projectId,
                                         final Long userId,
                                         final ProjectPermissionEnum permission) {
        final var now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        final var projectPermission = new ProjectPermissionModel();
        projectPermission.setId(id);
        projectPermission.setTenantId(tenantId);
        projectPermission.setProjectId(projectId);
        projectPermission.setCreated(now);
        projectPermission.setModified(now);
        projectPermission.setUserId(userId);
        projectPermission.setPermission(permission);
        projectPermission.setDeleted(false);
        return projectPermission;
    }
}
