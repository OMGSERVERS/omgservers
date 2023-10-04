package com.omgservers.module.user.impl.operation.selectUser;

import com.omgservers.model.user.UserModel;
import com.omgservers.model.user.UserRoleEnum;
import com.omgservers.operation.selectObject.SelectObjectOperation;
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
class SelectUserOperationImpl implements SelectUserOperation {

    final SelectObjectOperation selectObjectOperation;

    @Override
    public Uni<UserModel> selectUser(final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, created, modified, role, password_hash
                        from $schema.tab_user
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "User",
                this::createUser);
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
