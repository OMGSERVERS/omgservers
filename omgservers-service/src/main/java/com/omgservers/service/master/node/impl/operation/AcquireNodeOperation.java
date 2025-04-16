package com.omgservers.service.master.node.impl.operation;

import com.omgservers.schema.model.node.NodeModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface AcquireNodeOperation {
    Uni<NodeModel> execute(ChangeContext<?> changeContext, SqlConnection sqlConnection);
}
