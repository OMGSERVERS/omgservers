package com.omgservers.service.module.tenant.impl.operation.tenantBuildRequest;

import com.omgservers.schema.model.tenantBuildRequest.TenantBuildRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantBuildRequestOperation {
    Uni<TenantBuildRequestModel> execute(SqlConnection sqlConnection,
                                         int shard,
                                         Long tenantId,
                                         Long id);
}
