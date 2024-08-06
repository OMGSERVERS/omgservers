package com.omgservers.service.module.root.impl.operation.root.deleteRoot;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRootOperation {
    Uni<Boolean> deleteRoot(ChangeContext<?> changeContext,
                            SqlConnection sqlConnection,
                            int shard,
                            Long id);
}
