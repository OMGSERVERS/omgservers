package com.omgservers.schema.shard.pool.pool;

import com.omgservers.schema.shard.ShardRequest;
import com.omgservers.schema.model.pool.PoolModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPoolRequest implements ShardRequest {

    @NotNull
    PoolModel pool;

    @Override
    public String getRequestShardKey() {
        return pool.getId().toString();
    }
}
