package com.omgservers.service.module.tenant.impl.operation.tenant.hasTenant;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface HasTenantOperation {
    Uni<Boolean> hasTenant(SqlConnection sqlConnection,
                           int shard,
                           Long id);
}
