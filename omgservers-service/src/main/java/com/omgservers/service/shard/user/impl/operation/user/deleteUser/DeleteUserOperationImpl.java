package com.omgservers.service.shard.user.impl.operation.user.deleteUser;

import com.omgservers.service.event.body.module.user.UserDeletedEventBodyModel;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.shard.user.impl.operation.userPlayer.selectPlayer.SelectPlayerOperation;
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
class DeleteUserOperationImpl implements DeleteUserOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectPlayerOperation selectPlayerOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteUser(final ChangeContext<?> changeContext,
                                   final SqlConnection sqlConnection,
                                   final int shard,
                                   final Long id) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, shard,
                """
                        update $schema.tab_user
                        set modified = $2, deleted = true
                        where id = $1 and deleted = false
                        """,
                List.of(
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new UserDeletedEventBodyModel(id),
                () -> null
        );
    }
}
