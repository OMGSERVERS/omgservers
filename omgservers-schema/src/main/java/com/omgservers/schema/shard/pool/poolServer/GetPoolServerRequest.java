package com.omgservers.schema.shard.pool.poolServer;

import com.omgservers.schema.shard.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPoolServerRequest implements ShardedRequest {

    @NotNull
    Long poolId;

    @NotNull
    Long id;

    @Override
    public String getRequestShardKey() {
        return poolId.toString();
    }
}
