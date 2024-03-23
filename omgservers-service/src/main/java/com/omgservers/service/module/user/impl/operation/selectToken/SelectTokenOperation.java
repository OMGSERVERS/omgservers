package com.omgservers.service.module.user.impl.operation.selectToken;

import com.omgservers.model.token.TokenModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectTokenOperation {
    Uni<TokenModel> selectToken(SqlConnection sqlConnection, int shard, Long tokenId);
}
