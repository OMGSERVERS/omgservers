package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMatchmakerMatchStatusRequest implements ShardedRequest {

    @NotNull
    Long matchmakerId;

    @NotNull
    Long matchId;

    @NotNull
    MatchmakerMatchStatusEnum fromStatus;

    @NotNull
    MatchmakerMatchStatusEnum toStatus;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}
