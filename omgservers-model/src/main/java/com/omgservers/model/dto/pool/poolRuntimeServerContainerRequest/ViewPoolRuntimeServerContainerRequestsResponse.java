package com.omgservers.model.dto.pool.poolRuntimeServerContainerRequest;

import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoolRuntimeServerContainerRequestsResponse {

    List<PoolRuntimeServerContainerRequestModel> poolRuntimeServerContainerRequests;
}
