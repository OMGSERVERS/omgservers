package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.request.MatchmakerRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchmakerRequestRequest implements ShardedRequest {

    @NotNull
    MatchmakerRequestModel matchmakerRequest;

    @Override
    public String getRequestShardKey() {
        return matchmakerRequest.getMatchmakerId().toString();
    }
}
