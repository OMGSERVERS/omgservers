package com.omgservers.schema.module.pool.poolContainer;

import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPoolContainerResponse {

    PoolContainerModel poolContainer;
}
