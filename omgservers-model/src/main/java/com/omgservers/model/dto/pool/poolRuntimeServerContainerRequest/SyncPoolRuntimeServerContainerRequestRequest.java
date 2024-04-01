package com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyncPoolRuntimeServerContainerRequestRequest implements ShardedRequest {

    @NotNull
    PoolRuntimeServerContainerRequestModel poolRuntimeServerContainerRequest;

    @Override
    public String getRequestShardKey() {
        return poolRuntimeServerContainerRequest.getPoolId().toString();
    }
}
