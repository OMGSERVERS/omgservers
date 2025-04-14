package com.omgservers.schema.shard.tenant.tenantVersion;

import com.omgservers.schema.shard.tenant.tenantVersion.dto.TenantVersionDataDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantVersionDataResponse {

    TenantVersionDataDto tenantVersionData;
}
