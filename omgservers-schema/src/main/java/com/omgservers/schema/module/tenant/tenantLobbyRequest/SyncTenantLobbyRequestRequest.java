package com.omgservers.schema.module.tenant.tenantLobbyRequest;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.tenantLobbyRequest.TenantLobbyRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantLobbyRequestRequest implements ShardedRequest {

    @NotNull
    TenantLobbyRequestModel tenantLobbyRequest;

    @Override
    public String getRequestShardKey() {
        return tenantLobbyRequest.getTenantId().toString();
    }
}
