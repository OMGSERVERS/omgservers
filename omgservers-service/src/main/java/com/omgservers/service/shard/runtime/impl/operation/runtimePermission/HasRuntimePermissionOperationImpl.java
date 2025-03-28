package com.omgservers.service.shard.runtime.impl.operation.runtimePermission;

import com.omgservers.schema.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.service.operation.server.HasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasRuntimePermissionOperationImpl implements HasRuntimePermissionOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> execute(final SqlConnection sqlConnection,
                                final int shard,
                                final Long runtimeId,
                                final Long userId,
                                final RuntimePermissionEnum permission) {
        return hasObjectOperation.hasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_runtime_permission
                        where
                            runtime_id = $1 and user_id = $2 and permission = $3 and deleted = false
                        limit 1
                        """,
                List.of(runtimeId, userId, permission),
                "Runtime permission");
    }
}
