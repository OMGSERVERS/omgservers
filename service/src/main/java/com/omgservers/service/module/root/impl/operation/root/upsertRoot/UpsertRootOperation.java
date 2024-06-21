package com.omgservers.service.module.root.impl.operation.root.upsertRoot;

import com.omgservers.model.root.RootModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRootOperation {
    Uni<Boolean> upsertRoot(ChangeContext<?> changeContext,
                            SqlConnection sqlConnection,
                            int shard,
                            RootModel root);
}
