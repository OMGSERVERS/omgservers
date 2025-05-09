package com.omgservers.schema.shard.tenant.tenantPermission;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewTenantPermissionsRequest implements ShardRequest {

    @NotNull
    Long tenantId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
