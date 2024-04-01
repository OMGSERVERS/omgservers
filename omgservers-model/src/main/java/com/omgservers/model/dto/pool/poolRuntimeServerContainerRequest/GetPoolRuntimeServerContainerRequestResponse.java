package com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest;

import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPoolRuntimeServerContainerRequestResponse {

    PoolRuntimeServerContainerRequestModel poolRuntimeServerContainerRequest;
}
