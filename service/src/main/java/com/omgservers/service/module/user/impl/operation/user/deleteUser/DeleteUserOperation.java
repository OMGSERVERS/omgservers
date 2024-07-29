package com.omgservers.service.module.user.impl.operation.user.deleteUser;

import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteUserOperation {
    Uni<Boolean> deleteUser(ChangeContext<?> changeContext,
                            SqlConnection sqlConnection,
                            int shard,
                            final Long id);
}
