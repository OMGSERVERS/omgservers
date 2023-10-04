package com.omgservers.module.user.impl.operation.selectPlayerObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.player.PlayerObjectModel;
import com.omgservers.module.user.impl.mapper.PlayerModelMapper;
import com.omgservers.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectPlayerObjectOperationImpl implements SelectPlayerObjectOperation {

    final SelectObjectOperation selectObjectOperation;

    final PlayerModelMapper playerModelMapper;
    final ObjectMapper objectMapper;

    @Override
    public Uni<PlayerObjectModel> selectPlayerObject(final SqlConnection sqlConnection,
                                                     final int shard,
                                                     final Long userId,
                                                     final Long playerId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select object
                        from $schema.tab_user_player
                        where user_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(userId, playerId),
                "Player object",
                row -> {
                    try {
                        return objectMapper.readValue(row.getString("object"), PlayerObjectModel.class);
                    } catch (IOException e) {
                        throw new ServerSideConflictException(String.format("player object can't be parsed, " +
                                "userId=%d, playerId=%d", userId, playerId));
                    }
                });
    }
}
