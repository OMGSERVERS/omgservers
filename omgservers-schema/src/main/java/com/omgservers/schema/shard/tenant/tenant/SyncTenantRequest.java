package com.omgservers.schema.shard.tenant.tenant;

import com.omgservers.schema.shard.ShardRequest;
import com.omgservers.schema.model.tenant.TenantModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantRequest implements ShardRequest {

    @NotNull
    TenantModel tenant;

    @Override
    public String getRequestShardKey() {
        return tenant.getId().toString();
    }
}
