package com.omgservers.service.module.pool.impl.operation.poolServerContainer.upsertPoolServerContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.event.body.module.pool.PoolServerContainerCreatedEventBodyModel;
import com.omgservers.schema.model.poolSeverContainer.PoolServerContainerModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
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
class UpsertPoolServerContainerOperationImpl implements UpsertPoolServerContainerOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertPoolServerContainer(final ChangeContext<?> changeContext,
                                                  final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final PoolServerContainerModel poolServerContainer) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_pool_server_container(
                            id, idempotency_key, pool_id, server_id, created, modified, runtime_id, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        poolServerContainer.getId(),
                        poolServerContainer.getIdempotencyKey(),
                        poolServerContainer.getPoolId(),
                        poolServerContainer.getServerId(),
                        poolServerContainer.getCreated().atOffset(ZoneOffset.UTC),
                        poolServerContainer.getModified().atOffset(ZoneOffset.UTC),
                        poolServerContainer.getRuntimeId(),
                        getConfigString(poolServerContainer),
                        poolServerContainer.getDeleted()
                ),
                () -> new PoolServerContainerCreatedEventBodyModel(
                        poolServerContainer.getPoolId(),
                        poolServerContainer.getServerId(),
                        poolServerContainer.getId()),
                () -> null
        );
    }

    String getConfigString(PoolServerContainerModel poolServerContainer) {
        try {
            return objectMapper.writeValueAsString(poolServerContainer.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
