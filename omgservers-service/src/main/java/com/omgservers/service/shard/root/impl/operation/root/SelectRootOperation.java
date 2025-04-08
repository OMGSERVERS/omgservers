package com.omgservers.service.shard.root.impl.operation.root;

import com.omgservers.schema.model.root.RootModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRootOperation {
    Uni<RootModel> execute(SqlConnection sqlConnection,
                           int shard,
                           Long id);
}
