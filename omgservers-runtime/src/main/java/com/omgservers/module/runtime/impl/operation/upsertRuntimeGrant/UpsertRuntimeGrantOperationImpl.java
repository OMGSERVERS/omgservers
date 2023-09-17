package com.omgservers.module.runtime.impl.operation.upsertRuntimeGrant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertRuntimeGrantOperationImpl implements UpsertRuntimeGrantOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertRuntimeGrant(final ChangeContext<?> changeContext,
                                           final SqlConnection sqlConnection,
                                           final int shard,
                                           final RuntimeGrantModel runtimeGrant) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_runtime_grant(id, runtime_id, created, modified, entity_id, permission)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        runtimeGrant.getId(),
                        runtimeGrant.getRuntimeId(),
                        runtimeGrant.getCreated().atOffset(ZoneOffset.UTC),
                        runtimeGrant.getModified().atOffset(ZoneOffset.UTC),
                        runtimeGrant.getEntityId(),
                        runtimeGrant.getPermission()
                ),
                () -> null,
                () -> logModelFactory.create("Runtime grant was inserted, runtimeGrant=" + runtimeGrant)
        );
    }
}
