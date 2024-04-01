package com.omgservers.service.module.pool.impl.operation.poolRuntimeServerContainerRequest.upsertPoolRuntimeServerContainerRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.module.pool.PoolRuntimeServerContainerRequestCreatedEventBodyModel;
import com.omgservers.model.poolRuntimeServerContainerRequest.PoolRuntimeServerContainerRequestModel;
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
class UpsertPoolRuntimeServerContainerRequestOperationImpl implements UpsertPoolRuntimeServerContainerRequestOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertPoolRuntimeServerContainerRequest(final ChangeContext<?> changeContext,
                                                                final SqlConnection sqlConnection,
                                                                final int shard,
                                                                final PoolRuntimeServerContainerRequestModel poolRuntimeServerContainerRequest) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_pool_runtime_server_container_request(
                            id, idempotency_key, pool_id, created, modified, runtime_id, config, deleted
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        poolRuntimeServerContainerRequest.getId(),
                        poolRuntimeServerContainerRequest.getIdempotencyKey(),
                        poolRuntimeServerContainerRequest.getPoolId(),
                        poolRuntimeServerContainerRequest.getCreated().atOffset(ZoneOffset.UTC),
                        poolRuntimeServerContainerRequest.getModified().atOffset(ZoneOffset.UTC),
                        poolRuntimeServerContainerRequest.getRuntimeId(),
                        getConfigString(poolRuntimeServerContainerRequest),
                        poolRuntimeServerContainerRequest.getDeleted()
                ),
                () -> new PoolRuntimeServerContainerRequestCreatedEventBodyModel(
                        poolRuntimeServerContainerRequest.getPoolId(),
                        poolRuntimeServerContainerRequest.getId()),
                () -> null
        );
    }

    String getConfigString(final PoolRuntimeServerContainerRequestModel poolRuntimeServerContainerRequest) {
        try {
            return objectMapper.writeValueAsString(poolRuntimeServerContainerRequest.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.OBJECT_WRONG, e.getMessage(), e);
        }
    }
}
