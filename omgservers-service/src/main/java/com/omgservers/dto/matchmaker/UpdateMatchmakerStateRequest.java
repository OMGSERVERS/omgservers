package com.omgservers.dto.matchmaker;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.matchmakerChangeOfState.MatchmakerChangeOfState;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMatchmakerStateRequest implements ShardedRequest {

    @NotNull
    Long matchmakerId;

    @NotNull
    MatchmakerChangeOfState changeOfState;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}
