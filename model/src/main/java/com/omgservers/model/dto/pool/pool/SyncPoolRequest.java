package com.omgservers.model.dto.pool.pool;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.pool.PoolModel;
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
