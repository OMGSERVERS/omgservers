package com.omgservers.schema.shard.matchmaker.matchmaker;

import com.omgservers.schema.shard.ShardedRequest;
import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncMatchmakerRequest implements ShardedRequest {

    @NotNull
    MatchmakerModel matchmaker;

    @Override
    public String getRequestShardKey() {
        return matchmaker.getId().toString();
    }
}
