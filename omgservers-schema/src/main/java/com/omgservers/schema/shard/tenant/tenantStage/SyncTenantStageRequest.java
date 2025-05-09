package com.omgservers.schema.shard.tenant.tenantStage;

import com.omgservers.schema.shard.ShardRequest;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantStageRequest implements ShardRequest {

    @NotNull
    TenantStageModel tenantStage;

    @Override
    public String getRequestShardKey() {
        return tenantStage.getTenantId().toString();
    }
}
