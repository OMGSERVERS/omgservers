package com.omgservers.model.dto.tenant;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.versionLobbyRequest.VersionLobbyRequestModel;
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
