package com.omgservers.service.module.tenant.impl.operation.tenantStage;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface VerifyTenantStageExistsOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int shard,
                         Long tenantId,
                         Long id);
}
