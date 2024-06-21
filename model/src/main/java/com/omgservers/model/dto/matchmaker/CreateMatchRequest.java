package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
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
