package com.omgservers.module.user.impl.operation.upsertPlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.event.body.PlayerCreatedEventBodyModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChange.ExecuteChangeOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertPlayerOperationImpl implements UpsertPlayerOperation {

    final ExecuteChangeOperation executeChangeOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertPlayer(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final PlayerModel player) {
        return executeChangeOperation.executeChange(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_user_player(id, user_id, created, modified, stage_id, config)
                        values($1, $2, $3, $4, $5, $6)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        player.getId(),
                        player.getUserId(),
                        player.getCreated().atOffset(ZoneOffset.UTC),
                        player.getModified().atOffset(ZoneOffset.UTC),
                        player.getStageId(),
                        getConfigString(player)
                ),
                () -> new PlayerCreatedEventBodyModel(player.getUserId(), player.getStageId(), player.getId()),
                () -> logModelFactory.create("Player was inserted, player=" + player)
        );
    }

    String getConfigString(PlayerModel player) {
        try {
            return objectMapper.writeValueAsString(player.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
