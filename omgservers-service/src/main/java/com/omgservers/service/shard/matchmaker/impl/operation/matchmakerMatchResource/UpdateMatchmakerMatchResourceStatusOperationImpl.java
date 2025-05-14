package com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceStatusEnum;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
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
class UpdateMatchmakerMatchResourceStatusOperationImpl implements UpdateMatchmakerMatchResourceStatusOperation {

    final ChangeObjectOperation changeObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final Long matchmakerId,
                                final Long matchId,
                                final MatchmakerMatchResourceStatusEnum status) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        update $slot.tab_matchmaker_match_resource
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
