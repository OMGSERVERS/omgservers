package com.omgservers.service.shard.tenant.impl.operation.tenantStage;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface VerifyTenantStageExistsOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int slot,
                         Long tenantId,
                         Long id);
}
