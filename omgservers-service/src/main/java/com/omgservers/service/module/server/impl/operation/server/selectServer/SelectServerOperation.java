package com.omgservers.service.module.server.impl.operation.server.selectServer;

import com.omgservers.model.server.ServerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectServerOperation {
    Uni<ServerModel> selectServer(SqlConnection sqlConnection,
                                  int shard,
                                  Long id);
}
