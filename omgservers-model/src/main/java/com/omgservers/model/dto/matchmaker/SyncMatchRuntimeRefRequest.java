package com.omgservers.model.dto.matchmaker;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.matchRuntimeRef.MatchRuntimeRefModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchRuntimeRefRequest implements ShardedRequest {

    @NotNull
    MatchRuntimeRefModel matchRuntimeRef;

    @Override
    public String getRequestShardKey() {
        return matchRuntimeRef.getMatchmakerId().toString();
    }
}
