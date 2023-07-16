package com.omgservers.application.module.userModule.impl.operation.selectTokenOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.module.userModule.model.token.TokenModel;
import com.omgservers.application.exception.ServerSideBadRequestException;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectTokenOperationImpl implements SelectTokenOperation {

    static private final String sql = """
            select user_uuid, created, uuid, expire, hash
            from $schema.tab_user_token
            where uuid = $1
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<TokenModel> selectToken(final SqlConnection sqlConnection,
                                       final int shard,
                                       final UUID tokenUuid) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (tokenUuid == null) {
            throw new ServerSideBadRequestException("tokenUuid is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(tokenUuid))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        log.info("Token was found, uuid={}, shard={}", tokenUuid, shard);
                        return createToken(iterator.next());
                    } else {
                        throw new ServerSideNotFoundException(String.format("token was not found, tokenUuid=%s",
                                tokenUuid));
                    }
                });
    }

    TokenModel createToken(Row row) {
        TokenModel tokenModel = new TokenModel();
        tokenModel.setUser(row.getUUID("user_uuid"));
        tokenModel.setCreated(row.getOffsetDateTime("created").toInstant());
        tokenModel.setUuid(row.getUUID("uuid"));
        tokenModel.setExpire(row.getOffsetDateTime("expire").toInstant());
        tokenModel.setHash(row.getString("hash"));
        return tokenModel;
    }
}
