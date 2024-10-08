package com.omgservers.schema.module.docker;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerModel;
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
    PoolServerContainerModel poolServerContainer;
}
