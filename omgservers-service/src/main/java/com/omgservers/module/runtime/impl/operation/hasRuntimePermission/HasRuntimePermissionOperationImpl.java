package com.omgservers.module.runtime.impl.operation.hasRuntimePermission;

import com.omgservers.model.runtimePermission.RuntimePermissionEnum;
import com.omgservers.operation.hasObject.HasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasRuntimePermissionOperationImpl implements HasRuntimePermissionOperation {

    final HasObjectOperation hasObjectOperation;

    @Override
    public Uni<Boolean> hasTenantPermission(final SqlConnection sqlConnection,
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
                        where runtime_id = $1 and user_id = $2 and permission = $3
                        limit 1
                        """,
                Arrays.asList(runtimeId, userId, permission),
                "Runtime permission");
    }
}
