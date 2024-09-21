package com.omgservers.service.module.tenant.impl.operation.tenantJenkinsRequest;

import com.omgservers.schema.model.tenantJenkinsRequest.TenantJenkinsRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantJenkinsRequestOperation {
    Uni<TenantJenkinsRequestModel> execute(SqlConnection sqlConnection,
                                           int shard,
                                           Long tenantId,
                                           Long id);
}
