package com.omgservers.base.module.internal.impl.operation.getIndex;

import com.omgservers.model.index.IndexModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface GetIndexOperation {
    Uni<IndexModel> getIndex(SqlConnection sqlConnection, String name);
}
