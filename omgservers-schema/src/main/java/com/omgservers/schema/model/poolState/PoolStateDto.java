package com.omgservers.schema.model.poolState;

import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
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
    List<PoolServerModel> servers;

    @NotNull
    List<PoolContainerModel> containers;

    @NotNull
    List<PoolRequestModel> requests;
}
