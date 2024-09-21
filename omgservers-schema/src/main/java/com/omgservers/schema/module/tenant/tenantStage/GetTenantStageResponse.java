package com.omgservers.schema.module.tenant.tenantStage;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTenantStageResponse {

    TenantStageModel tenantStage;
}
