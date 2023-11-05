package com.omgservers.service.module.matchmaker.impl.operation.deleteMatch;

import com.omgservers.model.event.body.MatchDeletedEventBodyModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.matchmaker.impl.operation.selectMatch.SelectMatchOperation;
import com.omgservers.service.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
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
class DeleteMatchOperationImpl implements DeleteMatchOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectMatchOperation selectMatchOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteMatch(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final Long matchmakerId,
                                    final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_matchmaker_match
                        set modified = $3, deleted = true
                        where matchmaker_id = $1 and id = $2 and deleted = false
                        """,
                Arrays.asList(
                        matchmakerId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new MatchDeletedEventBodyModel(matchmakerId, id),
                () -> logModelFactory.create(String.format("Match was deleted, " +
                        "matchmakerId=%d, matchId=%d", matchmakerId, id))
        );
    }
}
