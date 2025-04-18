package com.omgservers.service.shard.client.impl.operation.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.body.module.client.ClientCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.system.LogModelFactory;
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
class UpsertClientOperationImpl implements UpsertClientOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertClient(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int slot,
                                     final ClientModel client) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_client(
                            id, idempotency_key, created, modified, user_id, player_id, deployment_id, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        client.getId(),
                        client.getIdempotencyKey(),
                        client.getCreated().atOffset(ZoneOffset.UTC),
                        client.getModified().atOffset(ZoneOffset.UTC),
                        client.getUserId(),
                        client.getPlayerId(),
                        client.getDeploymentId(),
                        getConfigString(client),
                        client.getDeleted()
                ),
                () -> new ClientCreatedEventBodyModel(client.getId()),
                () -> null
        );
    }

    String getConfigString(final ClientModel client) {
        try {
            return objectMapper.writeValueAsString(client.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
