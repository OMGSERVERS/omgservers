package com.omgservers.module.user.impl.operation.upsertToken;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.token.TokenModel;
import com.omgservers.module.user.impl.operation.encodeToken.EncodeTokenOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import com.omgservers.operation.getConfig.GetConfigOperation;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertTokenOperationImpl implements UpsertTokenOperation {

    static private final String sql = """
            insert into $schema.tab_user_token(id, user_id, created, expire, hash)
            values($1, $2, $3, $4, $5)
            on conflict (id) do
            nothing
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final EncodeTokenOperation encodeTokenOperation;
    final GenerateIdOperation generateIdOperation;
    final GetConfigOperation getConfigOperation;

    @Override
    public Uni<Boolean> upsertToken(final SqlConnection sqlConnection,
                                    final int shard,
                                    final TokenModel token) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (token == null) {
            throw new ServerSideBadRequestException("token is null");
        }

        return upsertQuery(sqlConnection, shard, token)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Token was inserted, token={}", token);
                    } else {
                        log.info("Token already exists, token={}", token);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertQuery(SqlConnection sqlConnection, int shard, TokenModel tokenModel) {
        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(Arrays.asList(
                        tokenModel.getId(),
                        tokenModel.getUserId(),
                        tokenModel.getCreated().atOffset(ZoneOffset.UTC),
                        tokenModel.getExpire().atOffset(ZoneOffset.UTC),
                        tokenModel.getHash())))
                .map(rowSet -> rowSet.rowCount() > 0);
    }
}
