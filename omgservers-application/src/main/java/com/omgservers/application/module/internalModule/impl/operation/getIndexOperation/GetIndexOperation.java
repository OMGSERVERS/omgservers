package com.omgservers.application.module.internalModule.impl.operation.getIndexOperation;

import com.omgservers.application.module.internalModule.model.index.IndexModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface GetIndexOperation {
    Uni<IndexModel> getIndex(SqlConnection sqlConnection, String name);
}
