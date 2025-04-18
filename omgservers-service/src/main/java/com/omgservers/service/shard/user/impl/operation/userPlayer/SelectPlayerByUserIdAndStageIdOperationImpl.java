package com.omgservers.service.shard.user.impl.operation.userPlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.service.shard.user.impl.mapper.PlayerModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectPlayerByUserIdAndStageIdOperationImpl implements SelectPlayerByUserIdAndStageIdOperation {

    final SelectObjectOperation selectObjectOperation;

    final PlayerModelMapper playerModelMapper;
    final ObjectMapper objectMapper;

    @Override
    public Uni<PlayerModel> execute(final SqlConnection sqlConnection,
                                    final int slot,
                                    final Long userId,
                                    final Long tenantStageId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select id, idempotency_key, user_id, created, modified, tenant_id, stage_id, profile, deleted
                        from $slot.tab_user_player
                        where user_id = $1 and stage_id = $2
                        order by id desc
                        limit 1
                        """,
                List.of(userId, tenantStageId),
                "Player",
                playerModelMapper::execute);
    }
}
