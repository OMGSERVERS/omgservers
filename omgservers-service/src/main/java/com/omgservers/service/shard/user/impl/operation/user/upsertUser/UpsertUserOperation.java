package com.omgservers.service.shard.user.impl.operation.user.upsertUser;

import com.omgservers.schema.model.user.UserModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertUserOperation {
    Uni<Boolean> upsertUser(ChangeContext<?> changeContext,
                            SqlConnection sqlConnection,
                            int shard,
                            UserModel user);
}
