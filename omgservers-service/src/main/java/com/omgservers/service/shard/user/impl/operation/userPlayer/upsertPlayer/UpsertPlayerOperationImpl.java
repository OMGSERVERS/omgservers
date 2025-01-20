package com.omgservers.service.shard.user.impl.operation.userPlayer.upsertPlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.service.event.body.module.user.PlayerCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.lobby.LogModelFactory;
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
class UpsertPlayerOperationImpl implements UpsertPlayerOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertPlayer(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final PlayerModel player) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        insert into $schema.tab_user_player(
                            id, idempotency_key, user_id, created, modified, tenant_id, stage_id, profile, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        player.getId(),
                        player.getIdempotencyKey(),
                        player.getUserId(),
                        player.getCreated().atOffset(ZoneOffset.UTC),
                        player.getModified().atOffset(ZoneOffset.UTC),
                        player.getTenantId(),
                        player.getStageId(),
                        getProfileString(player),
                        player.getDeleted()
                ),
                () -> new PlayerCreatedEventBodyModel(player.getUserId(), player.getId()),
                () -> null
        );
    }

    String getProfileString(final PlayerModel player) {
        try {
            return objectMapper.writeValueAsString(player.getProfile());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
