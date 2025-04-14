package com.omgservers.schema.shard.tenant.tenantProject.dto;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.project.TenantProjectModel;
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
    TenantProjectModel project;

    @NotNull
    List<AliasModel> aliases;

    @NotNull
    List<TenantProjectPermissionModel> projectPermissions;

    @NotNull
    List<TenantStageModel> projectStages;

    @NotNull
    List<TenantVersionProjectionModel> projectVersions;

    @NotNull
    List<AliasModel> projectAliases;
}
