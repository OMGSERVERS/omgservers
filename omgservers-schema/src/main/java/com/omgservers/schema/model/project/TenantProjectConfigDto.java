package com.omgservers.schema.model.project;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantProjectConfigDto {

    static public TenantProjectConfigDto create() {
        final var tenantProjectConfig = new TenantProjectConfigDto();
        tenantProjectConfig.setVersion(TenantProjectConfigVersionEnum.V1);
        return tenantProjectConfig;
    }

    @NotNull
    TenantProjectConfigVersionEnum version;
}
