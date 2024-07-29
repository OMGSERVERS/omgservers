package com.omgservers.schema.module.pool.poolServerContainer;

import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPoolServerContainerResponse {

    PoolServerContainerModel poolServerContainer;
}
