package com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerResource;

import com.omgservers.schema.model.tenantMatchmakerResource.TenantMatchmakerResourceModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTenantMatchmakerResourceOperation {
    Uni<TenantMatchmakerResourceModel> execute(SqlConnection sqlConnection,
                                               int shard,
                                               Long tenantId,
                                               Long id);
}
