package com.omgservers.schema.module.docker;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartDockerContainerRequest implements ShardedRequest {

    @NotNull
    PoolServerModel poolServer;

    @NotNull
    PoolContainerModel poolContainer;

    @Override
    public String getRequestShardKey() {
        return poolServer.getId().toString();
    }
}
