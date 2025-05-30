package com.omgservers.schema.shard.matchmaker.matchmakerMatchResource;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchmakerMatchResourceRequest implements ShardRequest {

    @NotNull
    MatchmakerMatchResourceModel matchmakerMatchResource;

    @Override
    public String getRequestShardKey() {
        return matchmakerMatchResource.getMatchmakerId().toString();
    }
}
