package com.omgservers.factory;

import com.omgservers.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.operation.generateId.GenerateIdOperation;
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
        Instant now = Instant.now().truncatedTo(ChronoUnit.MILLIS);

        ProjectPermissionModel model = new ProjectPermissionModel();
        model.setId(id);
        model.setTenantId(tenantId);
        model.setProjectId(projectId);
        model.setCreated(now);
        model.setUserId(userId);
        model.setPermission(permission);
        return model;
    }
}
