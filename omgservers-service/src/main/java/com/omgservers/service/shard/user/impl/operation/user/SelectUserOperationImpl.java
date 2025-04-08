package com.omgservers.service.shard.user.impl.operation.user;

import com.omgservers.schema.model.user.UserModel;
import com.omgservers.service.shard.user.impl.mapper.UserModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
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

    final UserModelMapper userModelMapper;

    @Override
    public Uni<UserModel> execute(final SqlConnection sqlConnection,
                                  final int shard,
                                  final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, created, modified, role, password_hash, deleted
                        from $schema.tab_user
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "User",
                userModelMapper::execute);
    }
}
