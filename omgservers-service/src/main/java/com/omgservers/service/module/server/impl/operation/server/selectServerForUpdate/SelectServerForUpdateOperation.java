package com.omgservers.service.module.server.impl.operation.server.selectServerForUpdate;

import com.omgservers.model.server.ServerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectServerForUpdateOperation {
    Uni<ServerModel> selectServerForUpdate(SqlConnection sqlConnection,
                                           int shard,
                                           Long id);
}
