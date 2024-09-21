package com.omgservers.schema.module.tenant.tenant.dto;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantPermission.TenantPermissionModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantDataDto {

    @NotNull
    TenantModel tenant;

    @NotNull
    List<TenantPermissionModel> tenantPermissions;

    @NotNull
    List<TenantProjectModel> projects;

    @NotNull
    List<TenantProjectPermissionModel> projectPermissions;

    @NotNull
    List<TenantStageModel> stages;

    @NotNull
    List<TenantStagePermissionModel> stagePermissions;
}
