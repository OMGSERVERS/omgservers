package com.omgservers.model.dto.pool.poolServer;

import com.omgservers.model.poolServer.PoolServerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewPoolServerResponse {

    List<PoolServerModel> poolServers;
}
