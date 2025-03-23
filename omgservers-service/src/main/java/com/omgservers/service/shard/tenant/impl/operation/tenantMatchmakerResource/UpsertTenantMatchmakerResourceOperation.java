package com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerResource;

import com.omgservers.schema.model.tenantMatchmakerResource.TenantMatchmakerResourceModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertTenantMatchmakerResourceOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         TenantMatchmakerResourceModel tenantMatchmakerResource);
}
