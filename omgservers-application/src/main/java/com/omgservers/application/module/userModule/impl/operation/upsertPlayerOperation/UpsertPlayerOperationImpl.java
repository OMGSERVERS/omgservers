package com.omgservers.application.module.userModule.impl.operation.upsertPlayerOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.base.impl.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.player.PlayerModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertPlayerOperationImpl implements UpsertPlayerOperation {

    static private final String sql = """
            insert into $schema.tab_user_player(id, user_id, created, modified, stage_id, config)
            values($1, $2, $3, $4, $5, $6)
            on conflict (user_id, stage_id) do
            update set modified = $3, config = $6
            returning xmax::text::int = 0 as inserted
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertPlayer(final SqlConnection sqlConnection,
                                     final int shard,
                                     final PlayerModel player) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (player == null) {
            throw new ServerSideBadRequestException("player is null");
        }

        return upsertQuery(sqlConnection, shard, player)
                .invoke(inserted -> {
                    if (inserted) {
                        log.info("Player was inserted, player={}", player);
                    } else {
                        log.info("Player was updated, player={}", player);
                    }
                });
    }

    Uni<Boolean> upsertQuery(SqlConnection sqlConnection, int shard, PlayerModel player) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(player.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(new ArrayList<>() {{
                        add(player.getId());
                        add(player.getUserId());
                        add(player.getCreated().atOffset(ZoneOffset.UTC));
                        add(player.getModified().atOffset(ZoneOffset.UTC));
                        add(player.getStageId());
                        add(configString);
                    }}))
                    .map(rowSet -> rowSet.iterator().next().getBoolean("inserted"));
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }
}
