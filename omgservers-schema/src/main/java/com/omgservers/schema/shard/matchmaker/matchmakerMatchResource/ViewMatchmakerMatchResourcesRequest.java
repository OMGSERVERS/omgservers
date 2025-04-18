package com.omgservers.schema.shard.matchmaker.matchmakerMatchResource;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewMatchmakerMatchResourcesRequest implements ShardRequest {

    @NotNull
    Long matchmakerId;

    @Override
    public String getRequestShardKey() {
        return matchmakerId.toString();
    }
}
