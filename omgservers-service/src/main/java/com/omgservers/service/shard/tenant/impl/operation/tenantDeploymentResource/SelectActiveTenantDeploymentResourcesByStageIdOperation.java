package com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantDeploymentResourcesByStageIdOperation {
    Uni<List<TenantDeploymentResourceModel>> execute(SqlConnection sqlConnection,
                                                     int slot,
                                                     Long tenantId,
                                                     Long tenantStageId);
}
