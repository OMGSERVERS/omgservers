package com.omgservers.schema.shard.pool.poolContainer;

import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoolContainersResponse {

    List<PoolContainerModel> poolContainers;
}
