package com.omgservers.service.module.tenant.impl.operation.tenantDeployment;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantDeploymentsByTenantVersionIdOperation {
    Uni<List<TenantDeploymentModel>> execute(SqlConnection sqlConnection,
                                             int shard,
                                             Long tenantId,
                                             Long tenantVersionId);
}
