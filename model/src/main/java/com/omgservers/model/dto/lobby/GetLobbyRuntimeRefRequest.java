package com.omgservers.model.dto.lobby;

import com.omgservers.model.dto.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetLobbyRuntimeRefRequest implements ShardedRequest {

    @NotNull
    Long lobbyId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return lobbyId.toString();
    }
}
