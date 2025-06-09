package com.omgservers.schema.shard.tenant.tenantStageState;

import com.omgservers.schema.model.tenantStageState.TenantStageStateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantStageStateResponse {

    TenantStageStateDto tenantStageState;
}