package com.omgservers.schema.module.pool.poolServerContainer;

import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoolServerContainersResponse {

    List<PoolServerContainerModel> poolServerContainers;
}
