package com.omgservers.service.module.tenant.impl.operation.hasStage;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasStageOperation {
    Uni<Boolean> hasStage(SqlConnection sqlConnection,
                          int shard,
                          Long tenantId,
                          Long id);
}
