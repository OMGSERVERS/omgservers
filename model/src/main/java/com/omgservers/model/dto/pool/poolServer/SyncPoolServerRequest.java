package com.omgservers.model.dto.pool.poolServer;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.poolServer.PoolServerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPoolServerRequest implements ShardedRequest {

    @NotNull
    PoolServerModel poolServer;

    @Override
    public String getRequestShardKey() {
        return poolServer.getPoolId().toString();
    }
}
