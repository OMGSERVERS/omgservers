package com.omgservers.schema.module.pool.pool;

import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.module.ShardedRequest;
import com.omgservers.schema.model.pool.PoolModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPoolRequest implements ShardedRequest {

    @NotNull
    PoolModel pool;

    @Override
    public String getRequestShardKey() {
        return pool.getId().toString();
    }
}
