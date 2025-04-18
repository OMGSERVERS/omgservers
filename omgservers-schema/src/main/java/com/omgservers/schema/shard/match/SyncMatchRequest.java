package com.omgservers.schema.shard.match;

import com.omgservers.schema.model.match.MatchModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchRequest implements ShardRequest {

    @NotNull
    MatchModel match;

    @Override
    public String getRequestShardKey() {
        return match.getId().toString();
    }
}
