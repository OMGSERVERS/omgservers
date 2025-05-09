package com.omgservers.service.shard.user.impl.operation.userPlayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.shard.user.impl.mapper.PlayerModelMapper;
import com.omgservers.service.operation.server.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectPlayerProfileOperationImpl implements SelectPlayerProfileOperation {

    final SelectObjectOperation selectObjectOperation;

    final PlayerModelMapper playerModelMapper;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Object> execute(final SqlConnection sqlConnection,
                               final int slot,
                               final Long userId,
                               final Long playerId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select profile
                        from $slot.tab_user_player
                        where user_id = $1 and id = $2
                        limit 1
                        """,
                List.of(userId, playerId),
                "Player profile",
                row -> {
                    try {
                        return objectMapper.readValue(row.getString("profile"), Object.class);
                    } catch (IOException e) {
                        throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                                String.format("player profile can't be parsed, userId=%d, playerId=%d",
                                        userId, playerId));
                    }
                });
    }
}
