package com.omgservers.schema.module.tenant.tenantLobbyRef;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.tenantLobbyRef.TenantLobbyRefModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncTenantLobbyRefRequest implements ShardedRequest {

    @NotNull
    TenantLobbyRefModel tenantLobbyRef;

    @Override
    public String getRequestShardKey() {
        return tenantLobbyRef.getTenantId().toString();
    }
}
