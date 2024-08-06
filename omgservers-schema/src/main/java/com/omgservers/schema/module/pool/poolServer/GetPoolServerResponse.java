package com.omgservers.schema.module.pool.poolServer;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPoolServerResponse {

    PoolServerModel poolServer;
}
