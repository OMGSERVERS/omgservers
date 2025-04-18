package com.omgservers.service.shard.runtime.impl.operation.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.service.event.body.module.runtime.RuntimeCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.system.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertRuntimeOperationImpl implements UpsertRuntimeOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final RuntimeModel runtime) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_runtime(
                            id, idempotency_key, created, modified, deployment_id, qualifier, user_id, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        runtime.getId(),
                        runtime.getIdempotencyKey(),
                        runtime.getCreated().atOffset(ZoneOffset.UTC),
                        runtime.getModified().atOffset(ZoneOffset.UTC),
                        runtime.getDeploymentId(),
                        runtime.getQualifier(),
                        runtime.getUserId(),
                        getConfigString(runtime),
                        runtime.getDeleted()
                ),
                () -> new RuntimeCreatedEventBodyModel(runtime.getId()),
                () -> null
        );
    }

    String getConfigString(RuntimeModel runtime) {
        try {
            return objectMapper.writeValueAsString(runtime.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
