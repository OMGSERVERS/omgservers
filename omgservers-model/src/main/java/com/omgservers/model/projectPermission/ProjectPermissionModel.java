package com.omgservers.model.projectPermission;

import com.omgservers.exception.ServerSideBadRequestException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPermissionModel {

    public static void validate(ProjectPermissionModel permission) {
        if (permission == null) {
            throw new ServerSideBadRequestException("permission is null");
        }
    }

    @NotNull
    Long id;

    @NotNull
    Long tenantId;

    @NotNull
    Long projectId;

    @NotNull
    Instant created;

    @NotNull
    Long userId;

    @NotNull
    ProjectPermissionEnum permission;
}
