package com.omgservers.schema.module.tenant.tenantStage.dto;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.model.tenantStagePermission.TenantStagePermissionModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantStageDataDto {

    @NotNull
    TenantStageModel stage;

    @NotNull
    List<AliasModel> aliases;

    @NotNull
    List<TenantStagePermissionModel> permissions;

    @NotNull
    List<TenantDeploymentResourceModel> deployments;
}
