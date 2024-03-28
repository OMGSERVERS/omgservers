package com.omgservers.service.module.server.impl.operation.selectActiveServerContainersByServerId;

import com.omgservers.model.serverContainer.ServerContainerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveServerContainersByServerIdOperation {
    Uni<List<ServerContainerModel>> selectActiveServerContainersByServerId(SqlConnection sqlConnection,
                                                                           int shard,
                                                                           Long serverId);
}
