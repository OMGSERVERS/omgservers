package com.omgservers.service.server.index.operation;

import com.omgservers.schema.model.index.IndexModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface GetIndexOperation {
    Uni<IndexModel> getIndex(SqlConnection sqlConnection);
}
