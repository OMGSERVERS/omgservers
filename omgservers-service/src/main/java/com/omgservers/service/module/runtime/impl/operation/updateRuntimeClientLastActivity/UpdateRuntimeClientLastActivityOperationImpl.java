package com.omgservers.service.module.runtime.impl.operation.updateRuntimeClientLastActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateRuntimeClientLastActivityOperationImpl implements UpdateRuntimeClientLastActivityOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> updateRuntimeClientLastActivity(final ChangeContext<?> changeContext,
                                                        final SqlConnection sqlConnection,
                                                        final int shard,
                                                        final Long runtimeId,
                                                        final Long clientId) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_runtime_client
                        set modified = $3, last_activity = $4
                        where runtime_id = $1 and client_id = $2
                        """,
                Arrays.asList(
                        runtimeId,
                        clientId,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> null,
                () -> null
        );
    }
}
