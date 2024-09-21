package com.omgservers.schema.module.tenant.tenantMatchmakerRef;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantMatchmakerRefRequest implements ShardedRequest {

    @NotNull
    TenantMatchmakerRefModel tenantMatchmakerRef;

    @Override
    public String getRequestShardKey() {
        return tenantMatchmakerRef.getTenantId().toString();
    }
}
