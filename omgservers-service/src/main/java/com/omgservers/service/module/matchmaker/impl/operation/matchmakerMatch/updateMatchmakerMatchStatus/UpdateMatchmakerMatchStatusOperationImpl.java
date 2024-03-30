package com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatch.updateMatchmakerMatchStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchStatusEnum;
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
class UpdateMatchmakerMatchStatusOperationImpl implements UpdateMatchmakerMatchStatusOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> updateMatchmakerMatchStatus(final ChangeContext<?> changeContext,
                                                    final SqlConnection sqlConnection,
                                                    final int shard,
                                                    final Long matchmakerId,
                                                    final Long matchId,
                                                    final MatchmakerMatchStatusEnum status) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_matchmaker_match
                        set modified = $3, status = $4
                        where matchmaker_id = $1 and id = $2
                        """,
                List.of(
                        matchmakerId,
                        matchId,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        status
                ),
                () -> null,
                () -> null
        );
    }
}
