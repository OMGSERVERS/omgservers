package com.omgservers.model.dto.lobby;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.lobby.LobbyModel;
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
