package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.matchmakerChangeOfState.MatchmakerChangeOfStateDto;
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
    MatchmakerChangeOfStateDto changeOfState;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}
