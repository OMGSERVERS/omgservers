package com.omgservers.service.module.root.impl.operation.selectRoot;

import com.omgservers.model.root.RootModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRootOperation {
    Uni<RootModel> selectRoot(SqlConnection sqlConnection,
                              int shard,
                              Long id);
}
