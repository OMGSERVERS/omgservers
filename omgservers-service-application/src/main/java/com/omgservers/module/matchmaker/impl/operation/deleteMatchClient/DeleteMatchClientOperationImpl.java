package com.omgservers.module.matchmaker.impl.operation.deleteMatchClient;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.event.body.MatchClientDeletedEventBodyModel;
import com.omgservers.module.matchmaker.impl.operation.selectMatchClient.SelectMatchClientOperation;
import com.omgservers.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteMatchClientOperationImpl implements DeleteMatchClientOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectMatchClientOperation selectMatchClientOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteMatchClient(final ChangeContext<?> changeContext,
                                          final SqlConnection sqlConnection,
                                          final int shard,
                                          final Long matchmakerId,
                                          final Long id) {
        return selectMatchClientOperation.selectMatchClient(sqlConnection, shard, matchmakerId, id)
                .flatMap(matchClient -> changeObjectOperation.changeObject(
                        changeContext, sqlConnection, shard,
                        """
                                delete from $schema.tab_matchmaker_match_client
                                where matchmaker_id = $1 and id = $2
                                """,
                        Arrays.asList(
                                matchmakerId,
                                id
                        ),
                        () -> new MatchClientDeletedEventBodyModel(matchClient),
                        () -> logModelFactory.create("Match client was deleted, matchClient=" + matchClient)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
