package com.omgservers.module.user.impl.operation.deletePlayer;

import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.model.event.body.PlayerDeletedEventBodyModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.module.user.impl.operation.selectPlayer.SelectPlayerOperation;
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
class DeletePlayerOperationImpl implements DeletePlayerOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectPlayerOperation selectPlayerOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deletePlayer(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long userId,
                                     final Long id) {
        return selectPlayerOperation.selectPlayer(sqlConnection, shard, userId, id)
                .flatMap(player -> changeObjectOperation.changeObject(
                        changeContext, sqlConnection, shard,
                        """
                                delete from $schema.tab_user_player
                                where user_id = $1 and id = $2
                                """,
                        Arrays.asList(
                                userId,
                                id
                        ),
                        () -> new PlayerDeletedEventBodyModel(player),
                        () -> logModelFactory.create("Player was deleted, player=" + player)
                ))
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(false);
    }
}
