package com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantDeploymentResourceOperation {
    Uni<TenantDeploymentResourceModel> execute(SqlConnection sqlConnection,
                                               int shard,
                                               Long tenantId,
                                               Long id);
}
