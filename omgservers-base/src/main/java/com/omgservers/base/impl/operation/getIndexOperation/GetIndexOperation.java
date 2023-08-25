package com.omgservers.base.impl.operation.getIndexOperation;

import com.omgservers.model.index.IndexModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface GetIndexOperation {
    Uni<IndexModel> getIndex(SqlConnection sqlConnection, String name);
}
