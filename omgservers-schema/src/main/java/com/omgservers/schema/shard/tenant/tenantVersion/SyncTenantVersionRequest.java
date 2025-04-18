package com.omgservers.schema.shard.tenant.tenantVersion;

import com.omgservers.schema.shard.ShardRequest;
import com.omgservers.schema.model.tenantVersion.TenantVersionModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantVersionRequest implements ShardRequest {

    @NotNull
    TenantVersionModel tenantVersion;

    @Override
    public String getRequestShardKey() {
        return tenantVersion.getTenantId().toString();
    }
}
