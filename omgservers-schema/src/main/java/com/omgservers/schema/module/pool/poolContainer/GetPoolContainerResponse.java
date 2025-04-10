package com.omgservers.schema.module.pool.poolContainer;

import com.omgservers.schema.model.poolContainer.PoolContainerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPoolContainerResponse {

    PoolContainerModel poolContainer;
}
