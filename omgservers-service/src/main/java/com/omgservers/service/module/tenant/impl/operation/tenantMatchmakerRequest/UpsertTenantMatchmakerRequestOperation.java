package com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRequest;

import com.omgservers.schema.model.tenantMatchmakerRequest.TenantMatchmakerRequestModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertTenantMatchmakerRequestOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         TenantMatchmakerRequestModel tenantMatchmakerRequest);
}
