package com.omgservers.service.module.system.impl.operation.index.getIndex;

import com.omgservers.schema.model.index.IndexModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface GetIndexOperation {
    Uni<IndexModel> getIndex(SqlConnection sqlConnection, Long id);
}
