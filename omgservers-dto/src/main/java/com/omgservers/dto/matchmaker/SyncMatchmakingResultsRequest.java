package com.omgservers.dto.matchmaker;

import com.omgservers.dto.ShardedRequest;
import com.omgservers.model.matchmakingResults.MatchmakingResultsModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchmakingResultsRequest implements ShardedRequest {

    @NotNull
    Long matchmakerId;

    @NotNull
    MatchmakingResultsModel matchmakingResultsModel;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}
