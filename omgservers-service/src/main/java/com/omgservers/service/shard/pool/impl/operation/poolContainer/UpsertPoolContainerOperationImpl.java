package com.omgservers.service.shard.pool.impl.operation.poolContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.poolSeverContainer.PoolContainerModel;
import com.omgservers.service.event.body.module.pool.PoolContainerCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
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
class UpsertPoolContainerOperationImpl implements UpsertPoolContainerOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final PoolContainerModel poolContainer) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_pool_container(
                            id, idempotency_key, pool_id, server_id, created, modified, runtime_id, runtime_qualifier, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        poolContainer.getId(),
                        poolContainer.getIdempotencyKey(),
                        poolContainer.getPoolId(),
                        poolContainer.getServerId(),
                        poolContainer.getCreated().atOffset(ZoneOffset.UTC),
                        poolContainer.getModified().atOffset(ZoneOffset.UTC),
                        poolContainer.getRuntimeId(),
                        poolContainer.getRuntimeQualifier(),
                        getConfigString(poolContainer),
                        poolContainer.getDeleted()
                ),
                () -> new PoolContainerCreatedEventBodyModel(
                        poolContainer.getPoolId(),
                        poolContainer.getServerId(),
                        poolContainer.getId()),
                () -> null
        );
    }

    String getConfigString(PoolContainerModel poolContainer) {
        try {
            return objectMapper.writeValueAsString(poolContainer.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
