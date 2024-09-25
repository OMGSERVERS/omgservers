package com.omgservers.schema.entrypoint.developer.dto.tenantProject;

import com.omgservers.schema.entrypoint.developer.dto.tenantStage.TenantStageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantProjectDashboardDto {

    TenantProjectDto tenantProject;

    List<TenantProjectPermissionDto> tenantProjectPermissions;

    List<TenantStageDto> tenantStages;

    List<TenantVersionProjectionDto> tenantVersionProjections;
}
