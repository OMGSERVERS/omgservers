package com.omgservers.module.matchmaker.impl.operation.deleteMatchClient;

import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChange.ExecuteChangeOperation;
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

    final ExecuteChangeOperation executeChangeOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteMatchClient(final ChangeContext<?> changeContext,
                                          final SqlConnection sqlConnection,
                                          final int shard,
                                          final Long matchmakerId,
                                          final Long id) {
        return executeChangeOperation.executeChange(
                changeContext, sqlConnection, shard,
                """
                        delete from $schema.tab_matchmaker_match_client
                        where matchmaker_id = $1 and id = $2
                        """,
                Arrays.asList(
                        matchmakerId,
                        id
                ),
                () -> null,
                () -> logModelFactory.create(String.format("Match client was deleted, " +
                        "matchmakerId=%d, id=%d", matchmakerId, id))
        );
    }
}
