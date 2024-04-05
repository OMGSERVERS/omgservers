package com.omgservers.model.dto.pool.poolServerContainer;

import com.omgservers.model.dto.ShardedRequest;
import com.omgservers.model.poolSeverContainer.PoolServerContainerModel;
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
