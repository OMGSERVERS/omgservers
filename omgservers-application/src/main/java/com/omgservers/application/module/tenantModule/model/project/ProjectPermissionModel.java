package com.omgservers.application.module.tenantModule.model.project;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPermissionModel {

    static public ProjectPermissionModel create(final UUID project,
                                                final UUID user,
                                                final ProjectPermissionEnum permission) {
        Instant now = Instant.now();

        ProjectPermissionModel model = new ProjectPermissionModel();
        model.setProject(project);
        model.setCreated(now);
        model.setUser(user);
        model.setPermission(permission);
        return model;
    }

    static public void validateTenantPermission(ProjectPermissionModel permission) {
        if (permission == null) {
            throw new ServerSideBadRequestException("permission is null");
        }
    }

    UUID project;
    @ToString.Exclude
    Instant created;
    UUID user;
    ProjectPermissionEnum permission;
}
