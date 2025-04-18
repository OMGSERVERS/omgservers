package com.omgservers.service.shard.matchmaker.impl.operation.matchmaker;

import com.omgservers.service.event.body.module.matchmaker.MatchmakerDeletedEventBodyModel;
import com.omgservers.service.factory.system.LogModelFactory;
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
class DeleteMatchmakerOperationImpl implements DeleteMatchmakerOperation {

    final ChangeObjectOperation changeObjectOperation;
    final SelectMatchmakerOperation selectMatchmakerOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final Long id) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        update $slot.tab_matchmaker
                        set modified = $2, deleted = true
                        where id = $1 and deleted = false
                        """,
                List.of(
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> new MatchmakerDeletedEventBodyModel(id),
                () -> null
        );
    }
}
