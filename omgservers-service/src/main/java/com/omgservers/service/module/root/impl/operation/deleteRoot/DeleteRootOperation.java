package com.omgservers.service.module.root.impl.operation.deleteRoot;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRootOperation {
    Uni<Boolean> deleteRoot(ChangeContext<?> changeContext,
                            SqlConnection sqlConnection,
                            int shard,
                            Long id);
}
