package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
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
