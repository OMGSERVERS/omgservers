package com.omgservers.service.server.service.index.operation.deleteIndex;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteIndexOperation {
    Uni<Boolean> deleteIndex(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             Long id);
}
