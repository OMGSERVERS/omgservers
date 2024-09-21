package com.omgservers.schema.module.tenant.tenantVersion;

import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantVersionResponse {

    TenantVersionModel tenantVersion;
}
