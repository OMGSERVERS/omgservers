package com.omgservers.schema.module.pool.poolRequest;

import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPoolRequestResponse {

    PoolRequestModel poolRequest;
}
