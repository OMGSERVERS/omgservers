package com.omgservers.dto.user;

import com.omgservers.model.player.PlayerModel;
import com.omgservers.dto.ShardedRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPlayerRequest implements ShardedRequest {

    public static void validate(SyncPlayerRequest request) {
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