package com.omgservers.service.module.root.impl.operation.rootEntityRef.upsertRootEntityRef;

import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertRootEntityRefOperation {
    Uni<Boolean> upsertRootEntityRef(ChangeContext<?> changeContext,
                                     SqlConnection sqlConnection,
                                     int shard,
                                     RootEntityRefModel rootEntityRefModel);
}
