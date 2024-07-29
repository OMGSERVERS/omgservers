package com.omgservers.schema.module.matchmaker;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchmakerMatchRuntimeRefRequest implements ShardedRequest {

    @NotNull
    MatchmakerMatchRuntimeRefModel matchmakerMatchRuntimeRef;

    @Override
    public String getRequestShardKey() {
        return matchmakerMatchRuntimeRef.getMatchmakerId().toString();
    }
}
