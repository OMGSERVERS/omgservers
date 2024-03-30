package com.omgservers.service.module.server.impl.operation.serverContainer.selectServerContainer;

import com.omgservers.model.serverContainer.ServerContainerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectServerContainerOperation {
    Uni<ServerContainerModel> selectServerContainer(SqlConnection sqlConnection,
                                                    int shard,
                                                    Long serverId,
                                                    Long id);
}
