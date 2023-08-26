package com.omgservers.application.module.userModule.impl.operation.upsertUserOperation;

import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.user.UserModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
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

    static private final String sql = """
            insert into $schema.tab_user(id, created, modified, role, password_hash)
            values($1, $2, $3, $4, $5)
            on conflict (id) do
            update set modified = $3, role = $4, password_hash = $5
            returning xmax::text::int = 0 as inserted
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> upsertUser(final SqlConnection sqlConnection,
                                   final int shard,
                                   final UserModel user) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (user == null) {
            throw new ServerSideBadRequestException("user is null");
        }

        return upsertQuery(sqlConnection, shard, user)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("User was inserted, user={}", user);
                    } else {
                        log.info("User was updated, user={}", user);
                    }
                });
    }

    Uni<Boolean> upsertQuery(SqlConnection sqlConnection, int shard, UserModel userModel) {
        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(Arrays.asList(
                        userModel.getId(),
                        userModel.getCreated().atOffset(ZoneOffset.UTC),
                        userModel.getModified().atOffset(ZoneOffset.UTC),
                        userModel.getRole(),
                        userModel.getPasswordHash())))
                .map(rowSet -> rowSet.iterator().next().getBoolean("inserted"));
    }
}
