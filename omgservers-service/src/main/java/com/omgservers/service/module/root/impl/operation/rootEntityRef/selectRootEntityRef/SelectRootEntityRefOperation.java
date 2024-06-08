package com.omgservers.service.module.root.impl.operation.rootEntityRef.selectRootEntityRef;

import com.omgservers.model.rootEntityRef.RootEntityRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectRootEntityRefOperation {
    Uni<RootEntityRefModel> selectRootEntityRef(SqlConnection sqlConnection,
                                                int shard,
                                                Long rootId,
                                                Long id);
}
