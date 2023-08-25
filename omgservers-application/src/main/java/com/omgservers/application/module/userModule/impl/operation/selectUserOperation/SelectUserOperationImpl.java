package com.omgservers.application.module.userModule.impl.operation.selectUserOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.base.impl.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectUserOperationImpl implements SelectUserOperation {

    static private final String sql = """
            select id, created, modified, role, password_hash
            from $schema.tab_user
            where id = $1
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<UserModel> selectUser(final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long id) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (id == null) {
            throw new ServerSideBadRequestException("id is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        return createUser(iterator.next());
                    } else {
                        throw new ServerSideNotFoundException("user was not found, id=" + id);
                    }
                });
    }

    UserModel createUser(Row row) {
        UserModel userModel = new UserModel();
        userModel.setId(row.getLong("id"));
        userModel.setCreated(row.getOffsetDateTime("created").toInstant());
        userModel.setModified(row.getOffsetDateTime("modified").toInstant());
        userModel.setRole(UserRoleEnum.valueOf(row.getString("role")));
        userModel.setPasswordHash(row.getString("password_hash"));
        return userModel;
    }
}
