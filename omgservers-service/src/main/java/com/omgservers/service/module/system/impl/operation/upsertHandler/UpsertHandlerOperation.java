package com.omgservers.service.module.system.impl.operation.upsertHandler;

import com.omgservers.model.handler.HandlerModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertHandlerOperation {
    Uni<Boolean> upsertHandler(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               HandlerModel handler);
}
