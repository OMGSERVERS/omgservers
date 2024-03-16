package com.omgservers.service.module.user.impl.operation.deletePlayer;

import com.omgservers.model.event.body.PlayerDeletedEventBodyModel;
import com.omgservers.service.factory.LogModelFactory;
import com.omgservers.service.module.user.impl.operation.selectPlayer.SelectPlayerOperation;
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
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_user_player
                        set modified = $3, deleted = true
                        where user_id = $1 and id = $2 and deleted = false
                        """,
                List.of(
                        userId,
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new PlayerDeletedEventBodyModel(userId, id),
                () -> null
        );
    }
}
