package com.omgservers.service.module.system.impl.operation.deleteEntity;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteEntityOperation {
    Uni<Boolean> deleteEntity(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              Long id);
}
