package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
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
