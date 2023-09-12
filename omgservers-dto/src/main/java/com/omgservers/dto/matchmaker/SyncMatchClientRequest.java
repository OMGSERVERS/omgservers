package com.omgservers.dto.matchmaker;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.matchClient.MatchClientModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchClientRequest implements ShardedRequest {

    public static void validate(SyncMatchClientRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    MatchClientModel matchClient;

    @Override
    public String getRequestShardKey() {
        return matchClient.getMatchmakerId().toString();
    }
}