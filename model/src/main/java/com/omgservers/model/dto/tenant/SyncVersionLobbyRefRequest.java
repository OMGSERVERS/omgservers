package com.omgservers.model.dto.tenant;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.versionLobbyRef.VersionLobbyRefModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncVersionLobbyRefRequest implements ShardedRequest {

    @NotNull
    VersionLobbyRefModel versionLobbyRef;

    @Override
    public String getRequestShardKey() {
        return versionLobbyRef.getTenantId().toString();
    }
}
