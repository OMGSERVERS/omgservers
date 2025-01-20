package com.omgservers.service.shard.tenant.impl.operation.tenantBuildRequest;

import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantBuildRequestsByTenantIdOperation {
    Uni<List<TenantBuildRequestModel>> execute(SqlConnection sqlConnection,
                                               int shard,
                                               Long tenantId);
}
