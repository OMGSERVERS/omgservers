package com.omgservers.service.shard.pool.impl.operation.poolRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.poolRequest.PoolRequestModel;
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
class UpsertPoolRequestOperationImpl implements UpsertPoolRequestOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final PoolRequestModel poolRequest) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_pool_request(
                            id, idempotency_key, pool_id, created, modified, runtime_id, runtime_qualifier, config,
                            deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        poolRequest.getId(),
                        poolRequest.getIdempotencyKey(),
                        poolRequest.getPoolId(),
                        poolRequest.getCreated().atOffset(ZoneOffset.UTC),
                        poolRequest.getModified().atOffset(ZoneOffset.UTC),
                        poolRequest.getRuntimeId(),
                        poolRequest.getRuntimeQualifier(),
                        getConfigString(poolRequest),
                        poolRequest.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }

    String getConfigString(final PoolRequestModel poolRequest) {
        try {
            return objectMapper.writeValueAsString(poolRequest.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
