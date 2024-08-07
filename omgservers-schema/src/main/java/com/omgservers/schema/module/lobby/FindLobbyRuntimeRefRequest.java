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
public class FindLobbyRuntimeRefRequest implements ShardedRequest {

    @NotNull
    Long lobbyId;

    @Override
    public String getRequestShardKey() {
        return lobbyId.toString();
    }
}
