package com.omgservers.schema.model.tenant;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantConfigDto {

    static public TenantConfigDto create() {
        final var tenantConfig = new TenantConfigDto();
        tenantConfig.setVersion(TenantConfigVersionEnum.V1);
        return tenantConfig;
    }

    @NotNull
    TenantConfigVersionEnum version;
}
