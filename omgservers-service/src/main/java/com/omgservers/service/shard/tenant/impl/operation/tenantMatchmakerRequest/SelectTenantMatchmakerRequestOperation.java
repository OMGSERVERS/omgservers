package com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerRequest;

import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantMatchmakerRequestOperation {
    Uni<TenantMatchmakerRequestModel> execute(SqlConnection sqlConnection,
                                              int shard,
                                              Long tenantId,
                                              Long id);
}
