package com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerRef;

import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantMatchmakerRefOperation {
    Uni<TenantMatchmakerRefModel> execute(SqlConnection sqlConnection,
                                          int shard,
                                          Long tenantId,
                                          Long id);
}
