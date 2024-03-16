package com.omgservers.service.module.matchmaker.impl.operation.updateMatchmakerMatchStoppedFlag;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateMatchmakerMatchStoppedFlagOperationImpl implements UpdateMatchmakerMatchStoppedFlagOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> updateMatchmakerMatchStoppedFlag(final ChangeContext<?> changeContext,
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
                List.of(
                        matchmakerId,
                        matchId,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        stopped
                ),
                () -> null,
                () -> null
        );
    }
}
