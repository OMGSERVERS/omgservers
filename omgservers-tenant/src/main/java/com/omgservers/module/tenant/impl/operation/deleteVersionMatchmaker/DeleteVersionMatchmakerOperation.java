package com.omgservers.module.tenant.impl.operation.deleteVersionMatchmaker;

import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteVersionMatchmakerOperation {
    Uni<Boolean> deleteVersionMatchmaker(ChangeContext<?> changeContext,
                                         SqlConnection sqlConnection,
                                         int shard,
                                         Long tenantId,
                                         Long id);
}
