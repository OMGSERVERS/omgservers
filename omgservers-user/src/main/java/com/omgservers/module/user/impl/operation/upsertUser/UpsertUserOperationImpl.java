package com.omgservers.module.user.impl.operation.upsertUser;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.event.body.UserCreatedEventBodyModel;
import com.omgservers.model.user.UserModel;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UpsertUserOperationImpl implements UpsertUserOperation {

    static private final String SQL = """
            insert into $schema.tab_user(id, created, modified, role, password_hash)
            values($1, $2, $3, $4, $5)
            on conflict (id) do
            nothing
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertUser(final ChangeContext<?> changeContext,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final UserModel user) {
        if (changeContext == null) {
            throw new IllegalArgumentException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (user == null) {
            throw new ServerSideBadRequestException("user is null");
        }

        return upsertObject(sqlConnection, shard, user)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, user))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, user))
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("User was inserted, shard={}, user={}", shard, user);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(SqlConnection sqlConnection, int shard, UserModel userModel) {
        String preparedSql = prepareShardSqlOperation.prepareShardSql(SQL, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(Arrays.asList(
                        userModel.getId(),
                        userModel.getCreated().atOffset(ZoneOffset.UTC),
                        userModel.getModified().atOffset(ZoneOffset.UTC),
                        userModel.getRole(),
                        userModel.getPasswordHash())))
                .map(rowSet -> rowSet.rowCount() > 0);
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final UserModel user) {
        if (objectWasInserted) {
            final var body = new UserCreatedEventBodyModel(user.getId());
            final var event = eventModelFactory.create(body);
            return upsertEventOperation.upsertEvent(changeContext, sqlConnection, event);
        } else {
            return Uni.createFrom().item(false);
        }
    }

    Uni<Boolean> upsertLog(final boolean objectWasInserted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final UserModel user) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create("User was created, user=" + user);
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
