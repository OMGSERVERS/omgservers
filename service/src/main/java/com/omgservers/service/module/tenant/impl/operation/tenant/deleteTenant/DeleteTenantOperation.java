package com.omgservers.service.module.tenant.impl.operation.tenant.deleteTenant;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteTenantOperation {
    Uni<Boolean> deleteTenant(ChangeContext<?> changeContext,
                              SqlConnection sqlConnection,
                              int shard,
                              Long id);
}
