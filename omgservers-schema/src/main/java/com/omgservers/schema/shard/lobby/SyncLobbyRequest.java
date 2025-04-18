package com.omgservers.schema.shard.lobby;

import com.omgservers.schema.shard.ShardRequest;
import com.omgservers.schema.model.lobby.LobbyModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncLobbyRequest implements ShardRequest {

    @NotNull
    LobbyModel lobby;

    @Override
    public String getRequestShardKey() {
        return lobby.getId().toString();
    }
}
