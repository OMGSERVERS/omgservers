package com.omgservers.service.shard.root.impl.operation.rootEntityRef;

import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveRootEntityRefByRootIdOperation {
    Uni<List<RootEntityRefModel>> execute(SqlConnection sqlConnection,
                                          int shard,
                                          Long rootId);
}
