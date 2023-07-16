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
            select created, modified, uuid, config
            from $schema.tab_user_player
            where user_uuid = $1 and stage = $2
            limit 1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<PlayerModel> selectPlayer(final SqlConnection sqlConnection,
                                         final int shard,
                                         final UUID user,
                                         final UUID stage) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (user == null) {
            throw new IllegalArgumentException("user is null");
        }
        if (stage == null) {
            throw new IllegalArgumentException("stage is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(user, stage))
                .map(RowSet::iterator)
                .map(iterator -> {
                    if (iterator.hasNext()) {
                        try {
                            final var player = createPlayer(user, stage, iterator.next());
                            log.info("Player was found, player={}", player);
                            return player;
                        } catch (IOException e) {
                            throw new ServerSideConflictException(String.format("player config can't be parsed, " +
                                    "user=%s, stage=%s", user, stage));
                        }
                    } else {
                        throw new ServerSideNotFoundException(String.format("player was not found, " +
                                "user=%s, stage=%s", user, stage));
                    }
                });
    }

    PlayerModel createPlayer(UUID userUuid, UUID stageUuid, Row row) throws IOException {
        PlayerModel player = new PlayerModel();
        player.setUser(userUuid);
        player.setCreated(row.getOffsetDateTime("created").toInstant());
        player.setModified(row.getOffsetDateTime("modified").toInstant());
        player.setUuid(row.getUUID("uuid"));
        player.setStage(stageUuid);
        player.setConfig(objectMapper.readValue(row.getString("config"), PlayerConfigModel.class));
        return player;
    }
}
