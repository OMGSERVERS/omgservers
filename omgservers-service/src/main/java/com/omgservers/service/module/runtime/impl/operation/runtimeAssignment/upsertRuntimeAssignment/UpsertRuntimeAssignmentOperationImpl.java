package com.omgservers.service.module.runtime.impl.operation.runtimeAssignment.upsertRuntimeAssignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.event.body.module.runtime.RuntimeAssignmentCreatedEventBodyModel;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.server.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
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
class UpsertRuntimeAssignmentOperationImpl implements UpsertRuntimeAssignmentOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertRuntimeAssignment(final ChangeContext<?> changeContext,
                                                final SqlConnection sqlConnection,
                                                final int shard,
                                                final RuntimeAssignmentModel runtimeAssignment) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_runtime_assignment(
                            id, idempotency_key, runtime_id, created, modified, client_id, last_activity, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        runtimeAssignment.getId(),
                        runtimeAssignment.getIdempotencyKey(),
                        runtimeAssignment.getRuntimeId(),
                        runtimeAssignment.getCreated().atOffset(ZoneOffset.UTC),
                        runtimeAssignment.getModified().atOffset(ZoneOffset.UTC),
                        runtimeAssignment.getClientId(),
                        runtimeAssignment.getLastActivity().atOffset(ZoneOffset.UTC),
                        getConfigString(runtimeAssignment),
                        runtimeAssignment.getDeleted()
                ),
                () -> new RuntimeAssignmentCreatedEventBodyModel(runtimeAssignment.getRuntimeId(),
                        runtimeAssignment.getId()),
                () -> null
        );
    }

    String getConfigString(RuntimeAssignmentModel runtimeAssignment) {
        try {
            return objectMapper.writeValueAsString(runtimeAssignment.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
