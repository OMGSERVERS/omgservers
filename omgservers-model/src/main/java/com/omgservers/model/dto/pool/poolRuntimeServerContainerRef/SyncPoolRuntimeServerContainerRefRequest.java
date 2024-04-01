package com.omgservers.model.dto.pool.poolRuntimeServerContainerRef;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.poolRuntimeSeverContainerRef.PoolRuntimeServerContainerRefModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPoolRuntimeServerContainerRefRequest implements ShardedRequest {

    @NotNull
    PoolRuntimeServerContainerRefModel poolRuntimeServerContainerRef;

    @Override
    public String getRequestShardKey() {
        return poolRuntimeServerContainerRef.getPoolId().toString();
    }
}
