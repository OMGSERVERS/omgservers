package com.omgservers.schema.module.tenant.tenantVersion;

import com.omgservers.schema.module.tenant.tenantVersion.dto.TenantVersionDataDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantVersionDataResponse {

    @NotNull
    TenantVersionDataDto tenantVersionData;
}
