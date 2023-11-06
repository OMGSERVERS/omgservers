package com.omgservers.service.module.user.impl.operation.deleteUser;

import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteUserOperation {
    Uni<Boolean> deleteUser(ChangeContext<?> changeContext,
                            SqlConnection sqlConnection,
                            int shard,
                            final Long id);
}
