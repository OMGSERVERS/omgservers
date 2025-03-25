package com.omgservers.schema.entrypoint.developer.dto.tenantStage;

import com.omgservers.schema.entrypoint.developer.dto.tenantDeployment.DeploymentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantStageDetailsDto {

    TenantStageDto stage;

    List<TenantStagePermissionDto> permissions;

    List<DeploymentDto> deployments;
}
