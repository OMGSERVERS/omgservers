package com.omgservers.service.shard.user.impl.operation.user;

import com.omgservers.schema.model.user.UserModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectUserOperation {
    Uni<UserModel> execute(SqlConnection sqlConnection,
                           int shard,
                           Long id);
}
