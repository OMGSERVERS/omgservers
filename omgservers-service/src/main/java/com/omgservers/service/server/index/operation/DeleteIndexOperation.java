package com.omgservers.service.server.index.operation;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteIndexOperation {
    Uni<Boolean> deleteIndex(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             Long id);
}
