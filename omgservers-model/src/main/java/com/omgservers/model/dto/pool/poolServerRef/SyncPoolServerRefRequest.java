package com.omgservers.model.dto.pool.poolServerRef;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.poolServerRef.PoolServerRefModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPoolServerRefRequest implements ShardedRequest {

    @NotNull
    PoolServerRefModel poolServerRef;

    @Override
    public String getRequestShardKey() {
        return poolServerRef.getPoolId().toString();
    }
}
