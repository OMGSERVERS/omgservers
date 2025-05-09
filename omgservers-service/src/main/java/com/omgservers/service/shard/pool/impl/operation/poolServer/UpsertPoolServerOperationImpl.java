package com.omgservers.service.shard.pool.impl.operation.poolServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.poolServer.PoolServerModel;
import com.omgservers.service.event.body.module.pool.PoolServerCreatedEventBodyModel;
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
class UpsertPoolServerOperationImpl implements UpsertPoolServerOperation {

    final ChangeObjectOperation changeObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final PoolServerModel poolServer) {
        return changeObjectOperation.execute(changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_pool_server(
                            id, idempotency_key, pool_id, created, modified, qualifier, config, status, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        poolServer.getId(),
                        poolServer.getIdempotencyKey(),
                        poolServer.getPoolId(),
                        poolServer.getCreated().atOffset(ZoneOffset.UTC),
                        poolServer.getModified().atOffset(ZoneOffset.UTC),
                        poolServer.getQualifier(),
                        getConfigString(poolServer),
                        poolServer.getStatus(),
                        poolServer.getDeleted()
                ),
                () -> new PoolServerCreatedEventBodyModel(poolServer.getPoolId(), poolServer.getId()),
                () -> null
        );
    }

    String getConfigString(final PoolServerModel poolServer) {
        try {
            return objectMapper.writeValueAsString(poolServer.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
