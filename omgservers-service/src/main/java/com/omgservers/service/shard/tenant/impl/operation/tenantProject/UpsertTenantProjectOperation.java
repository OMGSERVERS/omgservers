package com.omgservers.service.shard.tenant.impl.operation.tenantProject;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertTenantProjectOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         TenantProjectModel tenantProject);
}
