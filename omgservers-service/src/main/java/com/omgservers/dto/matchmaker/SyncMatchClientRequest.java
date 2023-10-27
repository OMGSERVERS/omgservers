package com.omgservers.dto.matchmaker;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.matchClient.MatchClientModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchClientRequest implements ShardedRequest {

    @NotNull
    MatchClientModel matchClient;

    @Override
    public String getRequestShardKey() {
        return matchClient.getMatchmakerId().toString();
    }
}
