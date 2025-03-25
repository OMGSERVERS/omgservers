package com.omgservers.schema.module.matchmaker.matchmakerState;

import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
import com.omgservers.schema.module.ShardedRequest;
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
    MatchmakerChangeOfStateDto matchmakerChangeOfState;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}
