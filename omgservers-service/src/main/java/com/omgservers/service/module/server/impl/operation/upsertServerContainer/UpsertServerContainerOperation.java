package com.omgservers.service.module.server.impl.operation.upsertServerContainer;

import com.omgservers.model.serverContainer.ServerContainerModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertServerContainerOperation {
    Uni<Boolean> upsertServerContainer(ChangeContext<?> changeContext,
                                       SqlConnection sqlConnection,
                                       int shard,
                                       ServerContainerModel serverContainer);
}
