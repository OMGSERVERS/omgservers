package com.omgservers.service.module.system.impl.operation.index.deleteIndex;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteIndexOperation {
    Uni<Boolean> deleteIndex(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             Long id);
}
