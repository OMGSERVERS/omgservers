package com.omgservers.module.tenant.impl.operation.deleteVersionRuntime;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteVersionRuntimeOperation {
    Uni<Boolean> deleteVersionRuntime(ChangeContext<?> changeContext,
                                      SqlConnection sqlConnection,
                                      int shard,
                                      Long tenantId,
                                      Long id);
}
