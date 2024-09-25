package com.omgservers.schema.entrypoint.support;

import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionQualifierEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTenantStagePermissionsSupportRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantStageId;

    @NotNull
    Long userId;

    @NotEmpty
    Set<TenantStagePermissionQualifierEnum> permissionsToDelete;
}
