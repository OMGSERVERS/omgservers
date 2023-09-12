package com.omgservers.module.user.impl.operation.selectPlayerByUserIdAndStageId;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.module.user.impl.mapper.PlayerModelMapper;
import com.omgservers.operation.executeSelectObject.ExecuteSelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectPlayerByUserIdAndStageIdOperationImpl implements SelectPlayerByUserIdAndStageIdOperation {

    final ExecuteSelectObjectOperation executeSelectObjectOperation;

    final PlayerModelMapper playerModelMapper;
    final ObjectMapper objectMapper;

    @Override
    public Uni<PlayerModel> selectPlayerByUserIdAndStageId(final SqlConnection sqlConnection,
                                                           final int shard,
                                                           final Long userId,
                                                           final Long stageId) {
        return executeSelectObjectOperation.executeSelectObject(
                sqlConnection,
                shard,
                """
                        select id, user_id, created, modified, tenant_id, stage_id, config
                        from $schema.tab_user_player
                        where user_id = $1 and stage_id = $2
                        limit 1
                        """,
                Arrays.asList(userId, stageId),
                "Player",
                playerModelMapper::fromRow);
    }
}
