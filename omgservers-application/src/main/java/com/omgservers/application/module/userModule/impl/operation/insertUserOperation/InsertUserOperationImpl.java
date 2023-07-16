package com.omgservers.application.module.userModule.impl.operation.insertUserOperation;

import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.exception.ServerSideBadRequestException;
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
class InsertUserOperationImpl implements InsertUserOperation {

    static private final String sql = """
            insert into $schema.tab_user(created, modified, uuid, role, password_hash)
            values($1, $2, $3, $4, $5)
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Void> insertUser(final SqlConnection sqlConnection,
                                final int shard,
                                final UserModel user) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (user == null) {
            throw new ServerSideBadRequestException("user is null");
        }

        return insertQuery(sqlConnection, shard, user)
                .invoke(voidItem -> log.info("User was inserted, user={}", user));
    }

    Uni<Void> insertQuery(SqlConnection sqlConnection, int shard, UserModel userModel) {
        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.from(Arrays.asList(
                        userModel.getCreated().atOffset(ZoneOffset.UTC),
                        userModel.getModified().atOffset(ZoneOffset.UTC),
                        userModel.getUuid(),
                        userModel.getRole(),
                        userModel.getPasswordHash())))
                .replaceWithVoid();
    }
}
