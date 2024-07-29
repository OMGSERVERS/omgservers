package com.omgservers.service.module.user.impl.operation.userPlayer.selectPlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.player.PlayerModel;
import com.omgservers.service.module.user.impl.mapper.PlayerModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectPlayerOperationImpl implements SelectPlayerOperation {

    final SelectObjectOperation selectObjectOperation;

    final PlayerModelMapper playerModelMapper;
    final ObjectMapper objectMapper;

    @Override
    public Uni<PlayerModel> selectPlayer(final SqlConnection sqlConnection,
                                         final int shard,
                                         final Long userId,
                                         final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, user_id, created, modified, tenant_id, stage_id, attributes, profile, deleted
                        from $schema.tab_user_player
                        where user_id = $1 and id = $2
                        limit 1
                        """,
                List.of(userId, id),
                "Player",
                playerModelMapper::fromRow);
    }
}
