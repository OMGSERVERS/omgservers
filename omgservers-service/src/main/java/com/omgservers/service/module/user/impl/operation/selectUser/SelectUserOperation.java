package com.omgservers.service.module.user.impl.operation.selectUser;

import com.omgservers.model.user.UserModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectUserOperation {
    Uni<UserModel> selectUser(SqlConnection sqlConnection,
                              int shard,
                              Long id);
}
