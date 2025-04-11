package com.omgservers.schema.model.tenantImage;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantImageConfigDto {

    static public TenantImageConfigDto create() {
        final var tenantImageConfig = new TenantImageConfigDto();
        tenantImageConfig.setVersion(TenantImageConfigVersionEnum.V1);
        return tenantImageConfig;
    }

    @NotNull
    TenantImageConfigVersionEnum version;
}
