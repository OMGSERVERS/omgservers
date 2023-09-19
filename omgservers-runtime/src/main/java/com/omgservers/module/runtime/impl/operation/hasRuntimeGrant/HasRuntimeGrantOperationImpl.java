package com.omgservers.module.runtime.impl.operation.hasRuntimeGrant;

import com.omgservers.model.runtimeGrant.RuntimeGrantPermissionEnum;
import com.omgservers.operation.executeHasObject.ExecuteHasObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HasRuntimeGrantOperationImpl implements HasRuntimeGrantOperation {

    final ExecuteHasObjectOperation executeHasObjectOperation;

    @Override
    public Uni<Boolean> hasRuntimeGrant(final SqlConnection sqlConnection,
                                        final int shard,
                                        final Long runtimeId,
                                        final Long entityId,
                                        final RuntimeGrantPermissionEnum permission) {
        return executeHasObjectOperation.executeHasObject(
                sqlConnection,
                shard,
                """
                        select id
                        from $schema.tab_runtime_grant
                        where runtime_id = $1 and entity_id = $2 and permission = $3
                        limit 1
                        """,
                Arrays.asList(runtimeId, entityId, permission),
                "Runtime grant");
    }
}
