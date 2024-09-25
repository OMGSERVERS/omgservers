package com.omgservers.schema.entrypoint.developer.dto.tenantStage;

import com.omgservers.schema.entrypoint.developer.dto.tenantDeployment.TenantDeploymentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantStageDashboardDto {

    TenantStageDto tenantStage;

    List<TenantStagePermissionDto> tenantStagePermissions;

    List<TenantDeploymentDto> tenantDeployments;
}
