package com.omgservers.model.dto.user;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.player.PlayerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPlayerRequest implements ShardedRequest {

    @NotNull
    PlayerModel player;

    @Override
    public String getRequestShardKey() {
        return player.getUserId().toString();
    }
}
