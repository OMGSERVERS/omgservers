package com.omgservers.model.dto.pool.poolRequest;

import com.omgservers.model.poolRequest.PoolRequestModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindPoolRequestResponse {

    PoolRequestModel poolRequest;
}
