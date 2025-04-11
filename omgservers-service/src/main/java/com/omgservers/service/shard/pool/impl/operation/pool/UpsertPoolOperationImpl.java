package com.omgservers.service.shard.pool.impl.operation.pool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.pool.PoolModel;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
import com.omgservers.service.event.body.module.pool.PoolCreatedEventBodyModel;
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
class UpsertPoolOperationImpl implements UpsertPoolOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final PoolModel pool) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, shard,
                """
                        insert into $shard.tab_pool(
                            id, idempotency_key, created, modified, config, deleted)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        pool.getId(),
                        pool.getIdempotencyKey(),
                        pool.getCreated().atOffset(ZoneOffset.UTC),
                        pool.getModified().atOffset(ZoneOffset.UTC),
                        getConfigString(pool),
                        pool.getDeleted()
                ),
                () -> new PoolCreatedEventBodyModel(pool.getId()),
                () -> null
        );
    }

    String getConfigString(final PoolModel pool) {
        try {
            return objectMapper.writeValueAsString(pool.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
