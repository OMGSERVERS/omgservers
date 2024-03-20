package com.omgservers.service.module.matchmaker.impl.operation.deleteMatchmakerMatchRuntimeRef;

import com.omgservers.model.event.body.module.MatchmakerMatchRuntimeRefDeletedEventBodyModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatchmakerMatchClient.SelectMatchmakerMatchClientOperation;
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
class DeleteMatchmakerMatchRuntimeRefOperationImpl implements DeleteMatchmakerMatchRuntimeRefOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectMatchmakerMatchClientOperation selectMatchmakerMatchClientOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteMatchmakerMatchRuntimeRef(final ChangeContext<?> changeContext,
                                                        final SqlConnection sqlConnection,
                                                        final int shard,
                                                        final Long matchmakerId,
                                                        final Long matchId,
                                                        final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_matchmaker_match_runtime_ref
                        set modified = $4, deleted = true
                        where matchmaker_id = $1 and match_id = $2 and id = $3 and deleted = false
                        """,
                List.of(
                        matchmakerId,
                        matchId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new MatchmakerMatchRuntimeRefDeletedEventBodyModel(matchmakerId, matchId, id),
                () -> null
        );
    }
}
