package com.omgservers.schema.model.tenantStageCommand.body;

import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandBodyDto;
import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OpenDeploymentTenantStageCommandBodyDto extends TenantStageCommandBodyDto {

    @NotNull
    Long deploymentId;

    @Override
    public TenantStageCommandQualifierEnum getQualifier() {
        return TenantStageCommandQualifierEnum.OPEN_DEPLOYMENT;
    }
}
