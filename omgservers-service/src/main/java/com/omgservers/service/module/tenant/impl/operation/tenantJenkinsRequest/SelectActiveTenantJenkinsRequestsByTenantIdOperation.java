package com.omgservers.service.module.tenant.impl.operation.tenantJenkinsRequest;

import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveTenantJenkinsRequestsByTenantIdOperation {
    Uni<List<TenantJenkinsRequestModel>> execute(SqlConnection sqlConnection,
                                                 int shard,
                                                 Long tenantId);
}
