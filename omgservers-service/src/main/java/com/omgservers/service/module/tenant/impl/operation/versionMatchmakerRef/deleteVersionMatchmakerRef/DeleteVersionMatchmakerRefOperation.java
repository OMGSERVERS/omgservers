package com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRef.deleteVersionMatchmakerRef;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteVersionMatchmakerRefOperation {
    Uni<Boolean> deleteVersionMatchmakerRef(ChangeContext<?> changeContext,
                                            SqlConnection sqlConnection,
                                            int shard,
                                            Long tenantId,
                                            Long id);
}
