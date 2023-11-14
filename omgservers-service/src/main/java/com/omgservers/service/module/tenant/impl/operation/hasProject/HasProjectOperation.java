package com.omgservers.service.module.tenant.impl.operation.hasProject;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasProjectOperation {
    Uni<Boolean> hasProject(SqlConnection sqlConnection,
                            int shard,
                            Long tenantId,
                            Long id);
}
