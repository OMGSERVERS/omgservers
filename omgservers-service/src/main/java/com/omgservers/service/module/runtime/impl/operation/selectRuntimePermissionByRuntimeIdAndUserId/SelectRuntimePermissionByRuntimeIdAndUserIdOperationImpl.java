package com.omgservers.service.module.runtime.impl.operation.selectRuntimePermissionByRuntimeIdAndUserId;

import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.service.module.runtime.impl.mapper.RuntimePermissionModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRuntimePermissionByRuntimeIdAndUserIdOperationImpl implements
        SelectRuntimePermissionByRuntimeIdAndUserIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final RuntimePermissionModelMapper runtimePermissionModelMapper;


    @Override
    public Uni<RuntimePermissionModel> selectRuntimePermissionByRuntimeIdAndUserId(final SqlConnection sqlConnection,
                                                                                   final int shard,
                                                                                   final Long runtimeId,
                                                                                   final Long userId,
                                                                                   final RuntimePermissionEnum permission) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select id, runtime_id, created, modified, user_id, permission, deleted
                        from $schema.tab_runtime_permission
                        where runtime_id = $1 and user_id = $2 and permission = $3
                        limit 1
                        """,
                Arrays.asList(runtimeId, userId, permission),
                "Runtime permission",
                runtimePermissionModelMapper::fromRow);
    }
}
