package com.omgservers.schema.module.tenant.tenant.dto;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.schema.model.project.TenantProjectModel;
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
    List<AliasModel> aliases;

    @NotNull
    List<TenantPermissionModel> tenantPermissions;

    @NotNull
    List<TenantProjectModel> tenantProjects;

    @NotNull
    List<AliasModel> tenantAliases;
}
