package com.omgservers.module.runtime.impl.operation.selectRuntimeGrant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.model.runtimeGrant.RuntimeGrantPermissionEnum;
import com.omgservers.operation.executeSelectObject.ExecuteSelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectRuntimeGrantOperationImpl implements SelectRuntimeGrantOperation {

    final ExecuteSelectObjectOperation executeSelectObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<RuntimeGrantModel> selectRuntimeGrant(final SqlConnection sqlConnection,
                                                     final int shard,
                                                     final Long runtimeId,
                                                     final Long id) {
        return executeSelectObjectOperation.executeSelectObject(
                sqlConnection,
                shard,
                """
                        select id, runtime_id, created, modified, entity_id, permission
                        from $schema.tab_runtime_grant
                        where runtime_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(runtimeId, id),
                "Runtime grant",
                this::createRuntimeGrant);
    }

    RuntimeGrantModel createRuntimeGrant(Row row) {
        final var runtimeGrant = new RuntimeGrantModel();
        runtimeGrant.setId(row.getLong("id"));
        runtimeGrant.setRuntimeId(row.getLong("runtime_id"));
        runtimeGrant.setCreated(row.getOffsetDateTime("created").toInstant());
        runtimeGrant.setModified(row.getOffsetDateTime("modified").toInstant());
        runtimeGrant.setEntityId(row.getLong("entity_id"));
        runtimeGrant.setPermission(RuntimeGrantPermissionEnum.valueOf(row.getString("permission")));
        return runtimeGrant;
    }
}
