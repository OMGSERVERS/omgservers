package com.omgservers.service.shard.pool.impl.operation.poolCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.poolCommand.PoolCommandModel;
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
class UpsertPoolCommandOperationImpl implements UpsertPoolCommandOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final PoolCommandModel poolCommand) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_pool_command(
                            id, idempotency_key, pool_id, created, modified, qualifier, body, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        poolCommand.getId(),
                        poolCommand.getIdempotencyKey(),
                        poolCommand.getPoolId(),
                        poolCommand.getCreated().atOffset(ZoneOffset.UTC),
                        poolCommand.getModified().atOffset(ZoneOffset.UTC),
                        poolCommand.getQualifier(),
                        getBodyString(poolCommand),
                        poolCommand.getDeleted()
                ),
                () -> null,
                () -> null
        );
    }

    String getBodyString(final PoolCommandModel poolCommand) {
        try {
            return objectMapper.writeValueAsString(poolCommand.getBody());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
