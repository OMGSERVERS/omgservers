package com.omgservers.schema.module.tenant.tenantProject.dto;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantImageRef.TenantImageRefModel;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionProjectionModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantProjectDataDto {

    @NotNull
    TenantProjectModel tenantProject;

    @NotNull
    List<TenantProjectPermissionModel> tenantProjectPermissions;

    @NotNull
    List<TenantStageModel> tenantStages;

    @NotNull
    List<TenantVersionProjectionModel> tenantVersionProjections;
}
