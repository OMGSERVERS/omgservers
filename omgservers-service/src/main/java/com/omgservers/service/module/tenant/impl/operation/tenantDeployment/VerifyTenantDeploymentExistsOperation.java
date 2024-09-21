package com.omgservers.service.module.tenant.impl.operation.tenantDeployment;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface VerifyTenantDeploymentExistsOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int shard,
                         Long tenantId,
                         Long id);
}
