package com.omgservers.schema.module.tenant;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.versionLobbyRequest.VersionLobbyRequestModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncVersionLobbyRequestRequest implements ShardedRequest {

    @NotNull
    VersionLobbyRequestModel versionLobbyRequest;

    @Override
    public String getRequestShardKey() {
        return versionLobbyRequest.getTenantId().toString();
    }
}
