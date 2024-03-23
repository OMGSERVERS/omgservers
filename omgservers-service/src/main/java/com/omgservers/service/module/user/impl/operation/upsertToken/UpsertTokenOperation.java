package com.omgservers.service.module.user.impl.operation.upsertToken;

import com.omgservers.model.token.TokenModel;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertTokenOperation {
    Uni<Boolean> upsertToken(ChangeContext<?> changeContext,
                             SqlConnection sqlConnection,
                             int shard,
                             TokenModel token);
}
