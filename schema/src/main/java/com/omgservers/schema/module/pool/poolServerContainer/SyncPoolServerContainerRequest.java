package com.omgservers.schema.module.pool.poolServerContainer;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPoolServerContainerRequest implements ShardedRequest {

    @NotNull
    PoolServerContainerModel poolServerContainer;

    @Override
    public String getRequestShardKey() {
        return poolServerContainer.getPoolId().toString();
    }
}
