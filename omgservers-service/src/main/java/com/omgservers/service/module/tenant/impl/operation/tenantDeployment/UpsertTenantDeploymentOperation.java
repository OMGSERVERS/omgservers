package com.omgservers.service.module.tenant.impl.operation.tenantDeployment;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertTenantDeploymentOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         TenantDeploymentModel tenantDeployment);
}
