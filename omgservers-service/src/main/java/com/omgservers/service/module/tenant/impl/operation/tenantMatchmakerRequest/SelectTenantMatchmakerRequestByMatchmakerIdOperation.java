package com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRequest;

import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantMatchmakerRequestByMatchmakerIdOperation {
    Uni<TenantMatchmakerRequestModel> execute(SqlConnection sqlConnection,
                                              int shard,
                                              Long tenantId,
                                              Long deploymentId,
                                              Long matchmakerId);
}
