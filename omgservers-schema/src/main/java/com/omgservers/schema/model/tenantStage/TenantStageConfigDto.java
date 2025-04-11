package com.omgservers.schema.model.tenantStage;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantStageConfigDto {

    static public TenantStageConfigDto create() {
        final var tenantStageConfig = new TenantStageConfigDto();
        tenantStageConfig.setVersion(TenantStageConfigVersionEnum.V1);
        return tenantStageConfig;
    }

    @NotNull
    TenantStageConfigVersionEnum version;
}
