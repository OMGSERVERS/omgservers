package com.omgservers.application.module.userModule.impl.operation.selectUserOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.module.userModule.model.user.UserModel;
import com.omgservers.application.module.userModule.model.user.UserRoleEnum;
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
class SelectUserOperationImpl implements SelectUserOperation {

    static private final String sql = """
            select created, modified, role, password_hash
            from $schema.tab_user where uuid = $1
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<UserModel> selectUser(final SqlConnection sqlConnection,
                                     final int shard,
                                     final UUID uuid) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (uuid == null) {
            throw new ServerSideBadRequestException("uuid is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(uuid))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        return createUser(uuid, iterator.next());
                    } else {
                        throw new ServerSideNotFoundException(String.format("user was not found, uuid=%s", uuid));
                    }
                });
    }

    UserModel createUser(UUID uuid, Row row) {
        UserModel userModel = new UserModel();
        userModel.setCreated(row.getOffsetDateTime("created").toInstant());
        userModel.setModified(row.getOffsetDateTime("modified").toInstant());
        userModel.setUuid(uuid);
        userModel.setRole(UserRoleEnum.valueOf(row.getString("role")));
        userModel.setPasswordHash(row.getString("password_hash"));
        return userModel;
    }
}
