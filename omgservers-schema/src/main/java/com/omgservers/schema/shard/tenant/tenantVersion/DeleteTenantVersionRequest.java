package com.omgservers.schema.shard.tenant.tenantVersion;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTenantVersionRequest implements ShardRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
