package com.omgservers.service.module.root.impl.operation.rootEntityRef.deleteRootEntityRef;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteRootEntityRefOperation {
    Uni<Boolean> deleteRootEntityRef(ChangeContext<?> changeContext,
                                     SqlConnection sqlConnection,
                                     int shard,
                                     Long rootId,
                                     Long id);
}
