package com.omgservers.service.module.user.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PlayerModelMapper {

    final ObjectMapper objectMapper;

    public PlayerModel fromRow(final Row row) {
        final var player = new PlayerModel();
        player.setId(row.getLong("id"));
        player.setIdempotencyKey(row.getString("idempotency_key"));
        player.setUserId(row.getLong("user_id"));
        player.setCreated(row.getOffsetDateTime("created").toInstant());
        player.setModified(row.getOffsetDateTime("modified").toInstant());
        player.setTenantId(row.getLong("tenant_id"));
        player.setStageId(row.getLong("stage_id"));
        player.setDeleted(row.getBoolean("deleted"));
        try {
            player.setProfile(objectMapper.readValue(row.getString("profile"), Object.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "player profile can't be parsed, player=" + player, e);
        }
        return player;
    }
}
