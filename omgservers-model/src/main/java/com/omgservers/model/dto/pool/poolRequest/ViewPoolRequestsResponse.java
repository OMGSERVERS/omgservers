package com.omgservers.model.dto.pool.poolRequest;

import com.omgservers.model.poolRequest.PoolRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoolRequestsResponse {

    List<PoolRequestModel> poolRequests;
}
