package com.omgservers.schema.shard.pool.poolServer;

import com.omgservers.schema.model.poolServer.PoolServerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoolServersResponse {

    List<PoolServerModel> poolServers;
}
