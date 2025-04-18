package com.omgservers.schema.shard.pool.poolContainer;

import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import com.omgservers.schema.shard.ShardRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPoolContainerRequest implements ShardRequest {

    @NotNull
    PoolContainerModel poolContainer;

    @Override
    public String getRequestShardKey() {
        return poolContainer.getPoolId().toString();
    }
}
