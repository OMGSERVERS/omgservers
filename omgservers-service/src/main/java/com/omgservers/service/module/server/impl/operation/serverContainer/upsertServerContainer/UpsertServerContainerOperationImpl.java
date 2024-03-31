package com.omgservers.service.module.server.impl.operation.serverContainer.upsertServerContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.module.server.ServerContainerCreatedEventBodyModel;
import com.omgservers.model.serverContainer.ServerContainerModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.lobby.LogModelFactory;
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
class UpsertServerContainerOperationImpl implements UpsertServerContainerOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertServerContainer(final ChangeContext<?> changeContext,
                                              final SqlConnection sqlConnection,
                                              final int shard,
                                              final ServerContainerModel serverContainer) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_server_container(
                            id, idempotency_key, server_id, created, modified, runtime_id, image, cpu_limit,
                            memory_limit, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        serverContainer.getId(),
                        serverContainer.getIdempotencyKey(),
                        serverContainer.getServerId(),
                        serverContainer.getCreated().atOffset(ZoneOffset.UTC),
                        serverContainer.getModified().atOffset(ZoneOffset.UTC),
                        serverContainer.getRuntimeId(),
                        serverContainer.getImage(),
                        serverContainer.getCpuLimit(),
                        serverContainer.getMemoryLimit(),
                        getConfigString(serverContainer),
                        serverContainer.getDeleted()
                ),
                () -> new ServerContainerCreatedEventBodyModel(serverContainer.getServerId(), serverContainer.getId()),
                () -> null
        );
    }

    String getConfigString(ServerContainerModel serverContainer) {
        try {
            return objectMapper.writeValueAsString(serverContainer.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.OBJECT_WRONG, e.getMessage(), e);
        }
    }
}
