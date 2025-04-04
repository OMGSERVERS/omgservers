package com.omgservers.service.shard.runtime.impl.operation.runtimePermission;

import com.omgservers.schema.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.service.shard.runtime.impl.mapper.RuntimePermissionModelMapper;
import com.omgservers.service.operation.server.SelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveRuntimePermissionsByRuntimeIdOperationImpl implements
        SelectActiveRuntimePermissionsByRuntimeIdOperation {

    final SelectListOperation selectListOperation;

    final RuntimePermissionModelMapper runtimePermissionModelMapper;

    @Override
    public Uni<List<RuntimePermissionModel>> execute(final SqlConnection sqlConnection,
                                                     final int shard,
                                                     final Long runtimeId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select id, idempotency_key, runtime_id, created, modified, user_id, permission, deleted
                        from $schema.tab_runtime_permission
                        where runtime_id = $1 and deleted = false
                        order by id asc
                        """,
                Collections.singletonList(runtimeId),
                "Runtime permission",
                runtimePermissionModelMapper::execute);
    }
}
