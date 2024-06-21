package com.omgservers.service.module.tenant.impl.operation.version.hasVersion;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasVersionOperation {
    Uni<Boolean> hasVersion(SqlConnection sqlConnection,
                            int shard,
                            Long tenantId,
                            Long id);
}
