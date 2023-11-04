package com.omgservers.module.matchmaker.impl.operation.deleteMatchmakerCommand;

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
class DeleteMatchmakerCommandOperationImpl implements DeleteMatchmakerCommandOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteMatchmakerCommand(final ChangeContext<?> changeContext,
                                                final SqlConnection sqlConnection,
                                                final int shard,
                                                final Long matchmakerId,
                                                final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        delete from $schema.tab_matchmaker_command
                        where matchmaker_id = $1 and id = $2
                        """,
                Arrays.asList(matchmakerId, id),
                () -> null,
                () -> logModelFactory.create(String.format("Matchmaker command was deleted, " +
                        "matchmakerId=%d, id=%d", matchmakerId, id))
        );
    }
}
