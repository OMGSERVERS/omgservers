package com.omgservers.service.module.user.impl.operation.user.upsertUser;

import com.omgservers.schema.model.user.UserModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertUserOperation {
    Uni<Boolean> upsertUser(ChangeContext<?> changeContext,
                            SqlConnection sqlConnection,
                            int shard,
                            UserModel user);
}
