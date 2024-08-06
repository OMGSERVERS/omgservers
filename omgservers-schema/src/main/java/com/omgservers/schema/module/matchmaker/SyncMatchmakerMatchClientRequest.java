package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchmakerMatchClientRequest implements ShardedRequest {

    @NotNull
    MatchmakerMatchClientModel matchmakerMatchClient;

    @Override
    public String getRequestShardKey() {
        return matchmakerMatchClient.getMatchmakerId().toString();
    }
}
