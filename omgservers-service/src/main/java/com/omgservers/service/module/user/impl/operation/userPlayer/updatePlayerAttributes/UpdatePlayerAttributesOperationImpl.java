package com.omgservers.service.module.user.impl.operation.userPlayer.updatePlayerAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.player.PlayerAttributesDto;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdatePlayerAttributesOperationImpl implements UpdatePlayerAttributesOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> updatePlayerAttributes(final ChangeContext<?> changeContext,
                                               final SqlConnection sqlConnection,
                                               final int shard,
                                               final Long userId,
                                               final Long playerId,
                                               final PlayerAttributesDto attributes) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_user_player
                        set modified = $3, attributes = $4
                        where user_id = $1 and id = $2
                        """,
                List.of(
                        userId,
                        playerId,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        getAttributesString(attributes)
                ),
                () -> null,
                () -> null
        );
    }

    String getAttributesString(PlayerAttributesDto attributes) {
        try {
            return objectMapper.writeValueAsString(attributes);
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
