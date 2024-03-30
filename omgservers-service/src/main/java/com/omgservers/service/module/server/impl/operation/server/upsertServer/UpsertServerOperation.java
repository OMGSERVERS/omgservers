package com.omgservers.service.module.server.impl.operation.server.upsertServer;

import com.omgservers.model.server.ServerModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertServerOperation {
    Uni<Boolean> upsertServer(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              ServerModel server);
}
