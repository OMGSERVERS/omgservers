package com.omgservers.application.module.tenantModule.model.project;

import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ProjectPermissionModelFactory {

    final GenerateIdOperation generateIdOperation;

    public ProjectPermissionModel create(
            final Long projectId,
            final Long userId,
            final ProjectPermissionEnum permission) {
        final var id = generateIdOperation.generateId();
        return create(id, projectId, userId, permission);
    }

    public ProjectPermissionModel create(final Long id,
                                         final Long projectId,
                                         final Long userId,
                                         final ProjectPermissionEnum permission) {
        Instant now = Instant.now();

        ProjectPermissionModel model = new ProjectPermissionModel();
        model.setId(id);
        model.setProjectId(projectId);
        model.setCreated(now);
        model.setUserId(userId);
        model.setPermission(permission);
        return model;
    }
}
