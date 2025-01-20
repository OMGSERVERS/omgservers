package com.omgservers.service.shard.user.impl.operation.user.selectUser;

import com.omgservers.schema.model.user.UserModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectUserOperation {
    Uni<UserModel> selectUser(SqlConnection sqlConnection,
                              int shard,
                              Long id);
}
