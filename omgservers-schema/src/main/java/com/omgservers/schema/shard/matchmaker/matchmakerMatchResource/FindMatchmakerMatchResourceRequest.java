package com.omgservers.schema.shard.matchmaker.matchmakerMatchResource;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindMatchmakerMatchResourceRequest implements ShardRequest {

    @NotNull
    Long matchmakerId;

    @NotNull
    Long matchId;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}
