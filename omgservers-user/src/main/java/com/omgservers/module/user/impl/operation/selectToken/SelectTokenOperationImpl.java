package com.omgservers.module.user.impl.operation.selectToken;

import com.omgservers.model.token.TokenModel;
import com.omgservers.operation.executeSelectObject.ExecuteSelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectTokenOperationImpl implements SelectTokenOperation {

    final ExecuteSelectObjectOperation executeSelectObjectOperation;

    @Override
    public Uni<TokenModel> selectToken(final SqlConnection sqlConnection,
                                       final int shard,
                                       final Long tokenId) {
        return executeSelectObjectOperation.executeSelectObject(
                sqlConnection,
                shard,
                """
                        select id, user_id, created, expire, hash
                        from $schema.tab_user_token
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(tokenId),
                "Token",
                this::createToken);
    }

    TokenModel createToken(Row row) {
        TokenModel tokenModel = new TokenModel();
        tokenModel.setId(row.getLong("id"));
        tokenModel.setUserId(row.getLong("user_id"));
        tokenModel.setCreated(row.getOffsetDateTime("created").toInstant());
        tokenModel.setExpire(row.getOffsetDateTime("expire").toInstant());
        tokenModel.setHash(row.getString("hash"));
        return tokenModel;
    }
}
