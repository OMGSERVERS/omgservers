package com.omgservers.module.system.impl.operation.upsertContainer;

import com.omgservers.model.container.ContainerModel;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertContainerOperation {
    Uni<Boolean> upsertContainer(ChangeContext<?> changeContext,
                                 SqlConnection sqlConnection,
                                 ContainerModel container);
}
