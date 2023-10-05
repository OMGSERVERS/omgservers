package com.omgservers.module.user.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.player.PlayerModel;
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

    public PlayerModel fromRow(Row row) {
        PlayerModel player = new PlayerModel();
        player.setId(row.getLong("id"));
        player.setUserId(row.getLong("user_id"));
        player.setCreated(row.getOffsetDateTime("created").toInstant());
        player.setModified(row.getOffsetDateTime("modified").toInstant());
        player.setTenantId(row.getLong("tenant_id"));
        player.setStageId(row.getLong("stage_id"));
        try {
            player.setAttributes(objectMapper.readValue(row.getString("attributes"), PlayerAttributesModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("player attributes can't be parsed, player=" + player, e);
        }
        try {
            player.setObject(objectMapper.readValue(row.getString("object"), Object.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("player object can't be parsed, player=" + player, e);
        }
        try {
            player.setConfig(objectMapper.readValue(row.getString("config"), PlayerConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("player config can't be parsed, player=" + player, e);
        }
        return player;
    }
}
