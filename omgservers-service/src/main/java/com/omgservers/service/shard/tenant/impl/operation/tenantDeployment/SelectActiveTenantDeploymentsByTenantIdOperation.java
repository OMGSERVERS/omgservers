package com.omgservers.service.shard.tenant.impl.operation.tenantDeployment;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantDeploymentsByTenantIdOperation {
    Uni<List<TenantDeploymentModel>> execute(SqlConnection sqlConnection,
                                             int shard,
                                             Long tenantId);
}
