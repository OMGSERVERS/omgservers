package com.omgservers.schema.entrypoint.support;

import com.omgservers.schema.model.projectPermission.ProjectPermissionEnum;
import com.omgservers.schema.model.tenantPermission.TenantPermissionEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProjectPermissionsSupportRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long projectId;

    @NotNull
    Long userId;

    @NotEmpty
    Set<ProjectPermissionEnum> permissionsToDelete;
}
