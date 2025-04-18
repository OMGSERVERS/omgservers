package com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentRef;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteTenantDeploymentRefOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         Long tenantId,
                         Long id);
}
