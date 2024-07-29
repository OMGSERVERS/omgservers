package com.omgservers.service.module.tenant.impl.operation.tenant.upsertTenant;

import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertTenantOperation {
    Uni<Boolean> upsertTenant(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              TenantModel tenant);
}
