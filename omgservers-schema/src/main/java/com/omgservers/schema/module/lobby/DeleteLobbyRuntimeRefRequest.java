package com.omgservers.schema.module.lobby;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteLobbyRuntimeRefRequest implements ShardedRequest {

    @NotNull
    Long lobbyId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return lobbyId.toString();
    }
}
