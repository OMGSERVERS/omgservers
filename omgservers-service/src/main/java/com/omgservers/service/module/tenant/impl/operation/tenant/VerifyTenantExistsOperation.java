package com.omgservers.service.module.tenant.impl.operation.tenant;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface VerifyTenantExistsOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int shard,
                         Long id);
}
