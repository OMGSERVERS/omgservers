package com.omgservers.module.user.impl.operation.deletePlayer;

import com.omgservers.model.event.body.PlayerDeletedEventBodyModel;
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
class DeletePlayerOperationImpl implements DeletePlayerOperation {

    final ExecuteChangeOperation executeChangeOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deletePlayer(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long userId,
                                     final Long id) {
        return executeChangeOperation.executeChange(
                changeContext, sqlConnection, shard,
                """
                        delete from $schema.tab_user_player
                        where user_id = $1 and id = $2
                        """,
                Arrays.asList(
                        userId,
                        id
                ),
                () -> new PlayerDeletedEventBodyModel(userId, id),
                () -> logModelFactory.create(String.format("Player was deleted, " +
                        "userId=%d, id=%d", userId, id))
        );
    }
}
