package com.omgservers.model.dto.pool.poolServerContainer;

import com.omgservers.model.poolSeverContainer.PoolServerContainerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPoolServerContainerResponse {

    PoolServerContainerModel poolServerContainer;
}
