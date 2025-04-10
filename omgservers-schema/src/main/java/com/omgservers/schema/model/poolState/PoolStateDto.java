package com.omgservers.schema.model.poolState;

import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.model.poolCommand.PoolCommandModel;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoolStateDto {

    @NotNull
    PoolModel pool;

    @NotNull
    List<PoolCommandModel> poolCommands;

    @NotNull
    List<PoolRequestModel> poolRequests;

    @NotNull
    List<PoolServerModel> poolServers;

    @NotNull
    List<PoolContainerModel> poolContainers;
}
