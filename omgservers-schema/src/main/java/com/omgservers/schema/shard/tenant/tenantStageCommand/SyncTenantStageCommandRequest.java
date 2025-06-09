package com.omgservers.schema.shard.tenant.tenantStageCommand;

import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantStageCommandRequest implements ShardRequest {

    @NotNull
    TenantStageCommandModel tenantStageCommand;

    @Override
    public String getRequestShardKey() {
        return tenantStageCommand.getTenantId().toString();
    }
}
