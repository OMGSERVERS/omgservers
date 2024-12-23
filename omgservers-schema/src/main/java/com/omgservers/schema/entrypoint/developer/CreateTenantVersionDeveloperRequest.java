package com.omgservers.schema.entrypoint.developer;

import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTenantVersionDeveloperRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long projectId;

    @NotNull
    TenantVersionConfigDto config;

}
