package com.omgservers.application.module.tenantModule.model.project;

import com.omgservers.application.exception.ServerSideBadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPermissionModel {

    static public void validate(ProjectPermissionModel permission) {
        if (permission == null) {
            throw new ServerSideBadRequestException("permission is null");
        }
    }

    Long id;
    Long projectId;
    Instant created;
    Long userId;
    ProjectPermissionEnum permission;
}
