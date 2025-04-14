package com.omgservers.schema.shard.tenant.tenant;

import com.omgservers.schema.shard.tenant.tenant.dto.TenantDataDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantDataResponse {

    TenantDataDto tenantData;
}
