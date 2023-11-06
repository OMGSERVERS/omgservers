package com.omgservers.service.module.runtime.impl.operation.upsertRuntimeGrant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertRuntimeGrant(final ChangeContext<?> changeContext,
                                           final SqlConnection sqlConnection,
                                           final int shard,
                                           final RuntimeGrantModel runtimeGrant) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_runtime_grant(
                            id, runtime_id, created, modified, shard_key, entity_id, type, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        runtimeGrant.getId(),
                        runtimeGrant.getRuntimeId(),
                        runtimeGrant.getCreated().atOffset(ZoneOffset.UTC),
                        runtimeGrant.getModified().atOffset(ZoneOffset.UTC),
                        runtimeGrant.getShardKey(),
                        runtimeGrant.getEntityId(),
                        runtimeGrant.getType(),
                        runtimeGrant.getDeleted()
                ),
                () -> null,
                () -> logModelFactory.create("Runtime grant was inserted, runtimeGrant=" + runtimeGrant)
        );
    }
}
