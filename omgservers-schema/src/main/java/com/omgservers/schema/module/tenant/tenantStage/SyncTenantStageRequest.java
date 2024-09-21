package com.omgservers.schema.module.tenant.tenantStage;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantStageRequest implements ShardedRequest {

    @NotNull
    TenantStageModel tenantStage;

    @Override
    public String getRequestShardKey() {
        return tenantStage.getTenantId().toString();
    }
}
