package com.omgservers.schema.shard.user;

import com.omgservers.schema.shard.ShardRequest;
import com.omgservers.schema.model.player.PlayerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPlayerRequest implements ShardRequest {

    @NotNull
    PlayerModel player;

    @Override
    public String getRequestShardKey() {
        return player.getUserId().toString();
    }
}
