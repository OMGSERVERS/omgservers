package com.omgservers.service.module.tenant.impl.operation.tenantVersion;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface VerifyTenantVersionExistsOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int shard,
                         Long tenantId,
                         Long id);
}
