package com.omgservers.schema.module.tenant.tenantLobbyResource;

import com.omgservers.schema.model.tenantLobbyResource.TenantLobbyResourceModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantLobbyResourceRequest implements ShardedRequest {

    @NotNull
    TenantLobbyResourceModel tenantLobbyResource;

    @Override
    public String getRequestShardKey() {
        return tenantLobbyResource.getTenantId().toString();
    }
}
