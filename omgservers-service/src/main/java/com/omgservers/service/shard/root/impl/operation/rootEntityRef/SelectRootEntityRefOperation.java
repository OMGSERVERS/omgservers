package com.omgservers.service.shard.root.impl.operation.rootEntityRef;

import com.omgservers.schema.model.rootEntityRef.RootEntityRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRootEntityRefOperation {
    Uni<RootEntityRefModel> execute(SqlConnection sqlConnection,
                                    int slot,
                                    Long rootId,
                                    Long id);
}
