package com.omgservers.schema.shard.pool.poolRequest;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPoolRequestResponse {

    PoolRequestModel poolRequest;
}
