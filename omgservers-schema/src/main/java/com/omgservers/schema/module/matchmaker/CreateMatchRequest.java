package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchRequest implements ShardedRequest {

    @NotNull
    MatchmakerMatchModel matchmakerMatch;

    @Override
    public String getRequestShardKey() {
        return matchmakerMatch.getMatchmakerId().toString();
    }
}
