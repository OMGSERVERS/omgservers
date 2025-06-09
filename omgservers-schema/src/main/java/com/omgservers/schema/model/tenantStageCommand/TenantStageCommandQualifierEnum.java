package com.omgservers.schema.model.tenantStageCommand;

import com.omgservers.schema.model.tenantStageCommand.body.OpenDeploymentTenantStageCommandBodyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TenantStageCommandQualifierEnum {
    OPEN_DEPLOYMENT(OpenDeploymentTenantStageCommandBodyDto.class);

    final Class<? extends TenantStageCommandBodyDto> bodyClass;
}
