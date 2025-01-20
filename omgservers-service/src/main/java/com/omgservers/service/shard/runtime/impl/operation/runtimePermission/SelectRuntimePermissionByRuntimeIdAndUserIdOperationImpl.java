package com.omgservers.service.shard.runtime.impl.operation.runtimePermission;

import com.omgservers.schema.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.schema.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.service.shard.runtime.impl.mapper.RuntimePermissionModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRuntimePermissionByRuntimeIdAndUserIdOperationImpl implements
        SelectRuntimePermissionByRuntimeIdAndUserIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final RuntimePermissionModelMapper runtimePermissionModelMapper;


    @Override
    public Uni<RuntimePermissionModel> execute(final SqlConnection sqlConnection,
                                               final int shard,
                                               final Long runtimeId,
                                               final Long userId,
                                               final RuntimePermissionEnum permission) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, runtime_id, created, modified, user_id, permission, deleted
                        from $schema.tab_runtime_permission
                        where runtime_id = $1 and user_id = $2 and permission = $3
                        order by id desc
                        limit 1
                        """,
                List.of(runtimeId, userId, permission),
                "Runtime permission",
                runtimePermissionModelMapper::execute);
    }
}
