package com.omgservers.application.module.userModule.impl.operation.selectPlayerOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.application.exception.ServerSideConflictException;
import com.omgservers.application.exception.ServerSideNotFoundException;
import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.application.module.userModule.model.player.PlayerConfigModel;
import com.omgservers.application.module.userModule.model.player.PlayerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectPlayerOperationImpl implements SelectPlayerOperation {

    static private final String sql = """
            select id, user_id, created, modified, stage_id, config
            from $schema.tab_user_player
            where user_id = $1 and stage_id = $2
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<PlayerModel> selectPlayer(final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long userId,
                                         final Long stageId) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("userId is null");
        }
        if (stageId == null) {
            throw new IllegalArgumentException("stageId is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(userId, stageId))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        try {
                            final var player = createPlayer(iterator.next());
                            log.info("Player was found, player={}", player);
                            return player;
                        } catch (IOException e) {
                            throw new ServerSideConflictException(String.format("player config can't be parsed, " +
                                    "userId=%s, stageId=%s", userId, stageId));
                        }
                    } else {
                        throw new ServerSideNotFoundException(String.format("player was not found, " +
                                "userId=%s, stageId=%s", userId, stageId));
                    }
                });
    }

    PlayerModel createPlayer(Row row) throws IOException {
        PlayerModel player = new PlayerModel();
        player.setId(row.getLong("id"));
        player.setUserId(row.getLong("user_id"));
        player.setCreated(row.getOffsetDateTime("created").toInstant());
        player.setModified(row.getOffsetDateTime("modified").toInstant());
        player.setStageId(row.getLong("stage_id"));
        player.setConfig(objectMapper.readValue(row.getString("config"), PlayerConfigModel.class));
        return player;
    }
}
