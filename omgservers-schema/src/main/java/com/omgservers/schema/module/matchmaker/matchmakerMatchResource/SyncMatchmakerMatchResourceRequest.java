package com.omgservers.schema.module.matchmaker.matchmakerMatchResource;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchmakerMatchResourceRequest implements ShardedRequest {

    @NotNull
    MatchmakerMatchResourceModel matchmakerMatchResource;

    @Override
    public String getRequestShardKey() {
        return matchmakerMatchResource.getMatchmakerId().toString();
    }
}
