package com.omgservers.service.shard.user.impl.operation.user;

import com.omgservers.service.event.body.module.user.UserDeletedEventBodyModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import com.omgservers.service.shard.user.impl.operation.userPlayer.SelectPlayerOperation;
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

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final Long id) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        update $slot.tab_user
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
