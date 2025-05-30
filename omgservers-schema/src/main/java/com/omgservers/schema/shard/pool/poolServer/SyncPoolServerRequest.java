package com.omgservers.schema.shard.pool.poolServer;

import com.omgservers.schema.shard.ShardRequest;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPoolServerRequest implements ShardRequest {

    @NotNull
    PoolServerModel poolServer;

    @Override
    public String getRequestShardKey() {
        return poolServer.getPoolId().toString();
    }
}
