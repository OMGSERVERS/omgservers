package com.omgservers.schema.shard.pool.poolContainer;

import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoolContainersRequest implements ShardRequest {

    @NotNull
    Long poolId;

    Long serverId;

    @Override
    public String getRequestShardKey() {
        return poolId.toString();
    }
}
