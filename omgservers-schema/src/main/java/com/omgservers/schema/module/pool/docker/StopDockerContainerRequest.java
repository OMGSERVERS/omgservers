package com.omgservers.schema.module.pool.docker;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StopDockerContainerRequest {

    @NotNull
    PoolServerModel poolServer;

    @NotNull
    PoolContainerModel poolContainer;
}
