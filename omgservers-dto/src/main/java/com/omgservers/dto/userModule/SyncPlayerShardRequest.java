package com.omgservers.dto.userModule;

import com.omgservers.model.player.PlayerModel;
import com.omgservers.dto.ShardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPlayerShardRequest implements ShardRequest {

    static public void validate(SyncPlayerShardRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    PlayerModel player;

    @Override
    public String getRequestShardKey() {
        return player.getUserId().toString();
    }
}
