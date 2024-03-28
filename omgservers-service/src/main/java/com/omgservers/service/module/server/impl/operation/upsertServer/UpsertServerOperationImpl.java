package com.omgservers.service.module.server.impl.operation.upsertServer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.module.server.ServerCreatedEventBodyModel;
import com.omgservers.model.server.ServerModel;
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
class UpsertServerOperationImpl implements UpsertServerOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertServer(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final ServerModel server) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_client(
                            id, idempotency_key, created, modified, pool_id, qualifier, ip_address, cpu_count,
                            memory_size, config, deleted,
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        server.getId(),
                        server.getIdempotencyKey(),
                        server.getCreated().atOffset(ZoneOffset.UTC),
                        server.getModified().atOffset(ZoneOffset.UTC),
                        server.getPoolId(),
                        server.getQualifier(),
                        server.getIpAddress(),
                        server.getCpuCount(),
                        server.getMemorySize(),
                        getConfigString(server),
                        server.getDeleted()
                ),
                () -> new ServerCreatedEventBodyModel(server.getId()),
                () -> null
        );
    }

    String getConfigString(final ServerModel server) {
        try {
            return objectMapper.writeValueAsString(server.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.OBJECT_WRONG, e.getMessage(), e);
        }
    }
}
