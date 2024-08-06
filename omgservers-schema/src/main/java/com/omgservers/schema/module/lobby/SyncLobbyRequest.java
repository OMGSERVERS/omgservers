package com.omgservers.schema.module.lobby;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.lobby.LobbyModel;
import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.lobby.LobbyModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncLobbyRequest implements ShardedRequest {

    @NotNull
    LobbyModel lobby;

    @Override
    public String getRequestShardKey() {
        return lobby.getId().toString();
    }
}
