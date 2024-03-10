package com.omgservers.service.module.user.impl.operation.selectPlayerProfile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.user.impl.mapper.PlayerModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
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
class SelectPlayerProfileOperationImpl implements SelectPlayerProfileOperation {

    final SelectObjectOperation selectObjectOperation;

    final PlayerModelMapper playerModelMapper;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Object> selectPlayerProfile(final SqlConnection sqlConnection,
                                           final int shard,
                                           final Long userId,
                                           final Long playerId) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                shard,
                """
                        select profile
                        from $schema.tab_user_player
                        where user_id = $1 and id = $2
                        limit 1
                        """,
                Arrays.asList(userId, playerId),
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
