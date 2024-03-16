package com.omgservers.service.module.user.impl.operation.upsertPlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.PlayerCreatedEventBodyModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.LogModelFactory;
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
                            id, idempotency_key, user_id, created, modified, tenant_id, stage_id, attributes, profile, 
                            deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
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
                        getAttributesString(player),
                        getProfileString(player),
                        player.getDeleted()
                ),
                () -> new PlayerCreatedEventBodyModel(player.getUserId(), player.getId()),
                () -> null
        );
    }

    String getAttributesString(final PlayerModel player) {
        try {
            return objectMapper.writeValueAsString(player.getAttributes());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.OBJECT_WRONG, e.getMessage(), e);
        }
    }

    String getProfileString(final PlayerModel player) {
        try {
            return objectMapper.writeValueAsString(player.getProfile());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.OBJECT_WRONG, e.getMessage(), e);
        }
    }
}
