package com.omgservers.model.dto.pool.poolServer;

import com.omgservers.model.poolServer.PoolServerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPoolServerResponse {

    PoolServerModel poolServer;
}
