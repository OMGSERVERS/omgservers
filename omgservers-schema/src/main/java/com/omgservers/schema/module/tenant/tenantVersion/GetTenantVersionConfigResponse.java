package com.omgservers.schema.module.tenant.tenantVersion;

import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantVersionConfigResponse {

    TenantVersionConfigDto tenantVersionConfig;
}
