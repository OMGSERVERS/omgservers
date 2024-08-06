package com.omgservers.schema.module.user;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.player.PlayerModel;
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
