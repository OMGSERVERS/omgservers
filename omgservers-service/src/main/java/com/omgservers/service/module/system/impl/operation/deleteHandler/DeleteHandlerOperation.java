package com.omgservers.service.module.system.impl.operation.deleteHandler;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteHandlerOperation {
    Uni<Boolean> deleteHandler(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               Long id);
}
