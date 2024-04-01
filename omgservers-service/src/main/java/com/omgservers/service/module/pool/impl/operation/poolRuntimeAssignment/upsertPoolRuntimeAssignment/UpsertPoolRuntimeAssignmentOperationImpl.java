package com.omgservers.service.module.pool.impl.operation.poolRuntimeAssignment.upsertPoolRuntimeAssignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.module.pool.PoolRuntimeAssignmentCreatedEventBodyModel;
import com.omgservers.model.poolRuntimeAssignment.PoolRuntimeAssignmentModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
class UpsertPoolRuntimeAssignmentOperationImpl implements UpsertPoolRuntimeAssignmentOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertPoolRuntimeAssignmentRequest(final ChangeContext<?> changeContext,
                                                           final SqlConnection sqlConnection,
                                                           final int shard,
                                                           final PoolRuntimeAssignmentModel poolRuntimeAssignment) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_pool_runtime_assignment(
                            id, idempotency_key, pool_id, created, modified, runtime_id, server_id, config, deleted
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        poolRuntimeAssignment.getId(),
                        poolRuntimeAssignment.getIdempotencyKey(),
                        poolRuntimeAssignment.getPoolId(),
                        poolRuntimeAssignment.getCreated().atOffset(ZoneOffset.UTC),
                        poolRuntimeAssignment.getModified().atOffset(ZoneOffset.UTC),
                        poolRuntimeAssignment.getRuntimeId(),
                        poolRuntimeAssignment.getServerId(),
                        getConfigString(poolRuntimeAssignment),
                        poolRuntimeAssignment.getDeleted()
                ),
                () -> new PoolRuntimeAssignmentCreatedEventBodyModel(
                        poolRuntimeAssignment.getPoolId(),
                        poolRuntimeAssignment.getId()),
                () -> null
        );
    }

    String getConfigString(final PoolRuntimeAssignmentModel poolRuntimeAssignmentModel) {
        try {
            return objectMapper.writeValueAsString(poolRuntimeAssignmentModel.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.OBJECT_WRONG, e.getMessage(), e);
        }
    }
}
