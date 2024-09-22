package com.omgservers.schema.module.tenant.tenantLobbyRequest;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindTenantLobbyRequestRequest implements ShardedRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long tenantDeploymentId;

    @NotNull
    Long lobbyId;

    @Override
    public String getRequestShardKey() {
        return tenantId.toString();
    }
}
