package com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteTenantDeploymentResourceOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         Long tenantId,
                         Long id);
}
