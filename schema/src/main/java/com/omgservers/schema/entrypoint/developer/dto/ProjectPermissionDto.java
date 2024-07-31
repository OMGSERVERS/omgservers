package com.omgservers.schema.entrypoint.developer.dto;

import com.omgservers.schema.model.projectPermission.ProjectPermissionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPermissionDto {

    Long id;

    Instant created;

    Long userId;

    ProjectPermissionEnum permission;
}
