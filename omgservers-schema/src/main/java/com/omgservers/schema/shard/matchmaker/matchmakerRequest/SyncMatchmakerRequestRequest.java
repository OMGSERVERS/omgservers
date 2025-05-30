package com.omgservers.schema.shard.matchmaker.matchmakerRequest;

import com.omgservers.schema.shard.ShardRequest;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchmakerRequestRequest implements ShardRequest {

    @NotNull
    MatchmakerRequestModel matchmakerRequest;

    @Override
    public String getRequestShardKey() {
        return matchmakerRequest.getMatchmakerId().toString();
    }
}
