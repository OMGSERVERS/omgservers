package com.omgservers.module.matchmaker.impl.operation.updateMatchStoppedFlag;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.event.body.MatchUpdatedEventBodyModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateMatchStoppedFlagOperationImpl implements UpdateMatchStoppedFlagOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> updateMatch(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final Long matchmakerId,
                                    final Long matchId,
                                    final Boolean stopped) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_matchmaker_match
                        set modified = $3, stopped = $4
                        where matchmaker_id = $1 and id = $2
                        """,
                Arrays.asList(
                        matchmakerId,
                        matchId,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        stopped
                ),
                () -> new MatchUpdatedEventBodyModel(matchmakerId, matchId),
                () -> logModelFactory.create(String.format("Match was marked as stopped, " +
                        "matchmakerId=%d, matchId=%d", matchmakerId, matchId))
        );
    }
}
