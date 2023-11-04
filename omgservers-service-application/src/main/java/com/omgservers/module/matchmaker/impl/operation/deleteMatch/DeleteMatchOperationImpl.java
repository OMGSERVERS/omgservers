package com.omgservers.module.matchmaker.impl.operation.deleteMatch;

import com.omgservers.model.event.body.MatchDeletedEventBodyModel;
import com.omgservers.module.matchmaker.impl.operation.selectMatch.SelectMatchOperation;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
                        set deleted = true
                        where matchmaker_id = $1 and id = $2 and deleted = false
                        """,
                Arrays.asList(
                        matchmakerId,
                        id
                ),
                () -> new MatchDeletedEventBodyModel(matchmakerId, id),
                () -> logModelFactory.create(String.format("Match was deleted, " +
                        "matchmakerId=%d, matchId=%d", matchmakerId, id))
        );
    }
}
