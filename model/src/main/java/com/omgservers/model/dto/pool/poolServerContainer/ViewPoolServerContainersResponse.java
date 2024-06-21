package com.omgservers.model.dto.pool.poolServerContainer;

import com.omgservers.model.poolSeverContainer.PoolServerContainerModel;
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
