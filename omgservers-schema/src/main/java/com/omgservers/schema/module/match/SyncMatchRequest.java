package com.omgservers.schema.module.match;

import com.omgservers.schema.model.match.MatchModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchRequest implements ShardedRequest {

    @NotNull
    MatchModel match;

    @Override
    public String getRequestShardKey() {
        return match.getId().toString();
    }
}
