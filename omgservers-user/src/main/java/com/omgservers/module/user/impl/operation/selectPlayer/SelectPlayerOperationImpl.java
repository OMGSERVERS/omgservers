package com.omgservers.module.user.impl.operation.selectPlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.player.PlayerConfigModel;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.operation.executeSelectObject.ExecuteSelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectPlayerOperationImpl implements SelectPlayerOperation {

    final ExecuteSelectObjectOperation executeSelectObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<PlayerModel> selectPlayer(final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long userId,
                                         final Long stageId) {
        return executeSelectObjectOperation.executeSelectObject(
                sqlConnection,
                shard,
                """
                        select id, user_id, created, modified, stage_id, config
                        from $schema.tab_user_player
                        where user_id = $1 and stage_id = $2
                        limit 1
                        """,
                Arrays.asList(userId, stageId),
                "Player",
                this::createPlayer);
    }

    PlayerModel createPlayer(Row row) {
        PlayerModel player = new PlayerModel();
        player.setId(row.getLong("id"));
        player.setUserId(row.getLong("user_id"));
        player.setCreated(row.getOffsetDateTime("created").toInstant());
        player.setModified(row.getOffsetDateTime("modified").toInstant());
        player.setStageId(row.getLong("stage_id"));
        try {
            player.setConfig(objectMapper.readValue(row.getString("config"), PlayerConfigModel.class));
        } catch (IOException e) {
            throw new ServerSideConflictException("player config can't be parsed, player=" + player, e);
        }
        return player;
    }
}
