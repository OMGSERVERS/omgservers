package com.omgservers.service.module.tenant.impl.operation.deleteVersion;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteVersionOperation {
    Uni<Boolean> deleteVersion(ChangeContext<?> changeContext,
                               SqlConnection sqlConnection,
                               int shard,
                               Long tenantId,
                               Long id);
}
