package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.match.MatchModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchRequest implements ShardedRequest {

    @NotNull
    MatchModel match;

    @Override
    public String getRequestShardKey() {
        return match.getMatchmakerId().toString();
    }
}
