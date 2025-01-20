package com.omgservers.service.shard.client.impl.operation.clientMatchmakerRef.upsertClientMatchmakerRef;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.clientMatchmakerRef.ClientMatchmakerRefModel;
import com.omgservers.service.event.body.module.client.ClientMatchmakerRefCreatedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertClientMatchmakerRefOperationImpl implements UpsertClientMatchmakerRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertClientMatchmakerRef(final ChangeContext<?> changeContext,
                                                  final SqlConnection sqlConnection,
                                                  final int shard,
                                                  final ClientMatchmakerRefModel clientMatchmakerRefModel) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_client_matchmaker_ref(
                            id, idempotency_key, client_id, created, modified, matchmaker_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        clientMatchmakerRefModel.getId(),
                        clientMatchmakerRefModel.getIdempotencyKey(),
                        clientMatchmakerRefModel.getClientId(),
                        clientMatchmakerRefModel.getCreated().atOffset(ZoneOffset.UTC),
                        clientMatchmakerRefModel.getModified().atOffset(ZoneOffset.UTC),
                        clientMatchmakerRefModel.getMatchmakerId(),
                        clientMatchmakerRefModel.getDeleted()
                ),
                () -> new ClientMatchmakerRefCreatedEventBodyModel(clientMatchmakerRefModel.getClientId(),
                        clientMatchmakerRefModel.getId()),
                () -> null
        );
    }
}
