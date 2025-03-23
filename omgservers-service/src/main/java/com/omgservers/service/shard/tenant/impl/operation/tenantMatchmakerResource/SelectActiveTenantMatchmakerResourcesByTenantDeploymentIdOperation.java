package com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerResource;

import com.omgservers.schema.model.tenantMatchmakerResource.TenantMatchmakerResourceModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantMatchmakerResourcesByTenantDeploymentIdOperation {
    Uni<List<TenantMatchmakerResourceModel>> execute(
            SqlConnection sqlConnection,
            int shard,
            Long tenantId,
            Long tenantDeploymentId);
}
