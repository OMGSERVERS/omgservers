package com.omgservers.schema.module.tenant;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.versionLobbyRef.VersionLobbyRefModel;
import com.omgservers.schema.module.ShardedRequest;
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
