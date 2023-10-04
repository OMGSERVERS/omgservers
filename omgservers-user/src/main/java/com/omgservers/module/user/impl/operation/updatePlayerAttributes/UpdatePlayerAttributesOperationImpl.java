package com.omgservers.module.user.impl.operation.updatePlayerAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdatePlayerAttributesOperationImpl implements UpdatePlayerAttributesOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> updatePlayerAttributes(final ChangeContext<?> changeContext,
                                               final SqlConnection sqlConnection,
                                               final int shard,
                                               final Long userId,
                                               final Long playerId,
                                               final PlayerAttributesModel attributes) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_user_player
                        set modified = $3, attributes = $4
                        where user_id = $1 and id = $2
                        """,
                Arrays.asList(
                        userId,
                        playerId,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        getAttributesString(attributes)
                ),
                () -> null,
                () -> logModelFactory.create(String.format("Player attributes were updated, " +
                        "userId=%d, playerId=%s", userId, playerId))
        );
    }

    String getAttributesString(PlayerAttributesModel attributes) {
        try {
            return objectMapper.writeValueAsString(attributes);
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
