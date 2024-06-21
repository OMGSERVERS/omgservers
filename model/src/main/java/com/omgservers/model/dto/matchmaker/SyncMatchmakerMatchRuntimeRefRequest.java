package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
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
