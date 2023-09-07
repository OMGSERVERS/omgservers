package com.omgservers.module.matchmaker.impl.operation.updateMatchConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.event.body.MatchUpdatedEventBodyModel;
import com.omgservers.model.match.MatchConfigModel;
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
class UpdateMatchConfigOperationImpl implements UpdateMatchConfigOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> updateMatch(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final Long matchmakerId,
                                    final Long matchId,
                                    final MatchConfigModel config) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_matchmaker_match
                        set modified = $3, config = $4
                        where matchmaker_id = $1 and id = $2
                        """,
                Arrays.asList(
                        matchmakerId,
                        matchId,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        getConfigString(config)
                ),
                () -> new MatchUpdatedEventBodyModel(matchmakerId, matchId),
                () -> logModelFactory.create(String.format("Match was updated, " +
                        "matchmakerId=%d, matchId=%d", matchmakerId, matchId))
        );
    }

    String getConfigString(MatchConfigModel config) {
        try {
            return objectMapper.writeValueAsString(config);
        } catch (IOException e) {
            throw new ServerSideBadRequestException(e.getMessage(), e);
        }
    }
}
