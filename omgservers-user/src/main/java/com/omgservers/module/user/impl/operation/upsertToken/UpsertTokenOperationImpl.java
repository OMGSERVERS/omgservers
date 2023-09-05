package com.omgservers.module.user.impl.operation.upsertToken;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.token.TokenModel;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
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
    final UpsertEventOperation upsertEventOperation;
    final EncodeTokenOperation encodeTokenOperation;
    final GenerateIdOperation generateIdOperation;
    final GetConfigOperation getConfigOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;


    @Override
    public Uni<Boolean> upsertToken(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final TokenModel token) {
        if (changeContext == null) {
            throw new IllegalArgumentException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (token == null) {
            throw new ServerSideBadRequestException("token is null");
        }

        return upsertObject(sqlConnection, shard, token)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, token))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, token))
                .invoke(objectWasInserted -> {
                    if (objectWasInserted) {
                        log.info("Token was inserted, shard={}, token={}", shard, token);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(SqlConnection sqlConnection, int shard, TokenModel tokenModel) {
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

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final TokenModel token) {
        return Uni.createFrom().item(false);
    }

    Uni<Boolean> upsertLog(final boolean objectWasInserted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final TokenModel token) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create("Token was created, token=" + token);
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
