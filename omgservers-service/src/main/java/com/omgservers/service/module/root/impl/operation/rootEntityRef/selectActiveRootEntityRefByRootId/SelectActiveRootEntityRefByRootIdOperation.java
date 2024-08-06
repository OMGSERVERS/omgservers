package com.omgservers.service.module.root.impl.operation.rootEntityRef.selectActiveRootEntityRefByRootId;

import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveRootEntityRefByRootIdOperation {
    Uni<List<RootEntityRefModel>> selectActiveRootEntityRefByRootId(
            SqlConnection sqlConnection,
            int shard,
            Long rootId);
}
