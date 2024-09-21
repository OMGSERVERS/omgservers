package com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRequest;

import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantMatchmakerRequestsByTenantDeploymentIdOperation {
    Uni<List<TenantMatchmakerRequestModel>> execute(
            SqlConnection sqlConnection,
            int shard,
            Long tenantId,
            Long tenantDeploymentId);
}
