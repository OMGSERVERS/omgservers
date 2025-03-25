package com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentRef;

import com.omgservers.schema.model.tenantDeploymentRef.TenantDeploymentRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantDeploymentRefByDeploymentIdOperation {
    Uni<TenantDeploymentRefModel> execute(SqlConnection sqlConnection,
                                          int shard,
                                          Long tenantId,
                                          Long deploymentId);
}
