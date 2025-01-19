package com.omgservers.service.service.event.operation.deleteEventsByIds;

import com.omgservers.service.factory.lobby.LogModelFactory;
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
class DeleteEventsByIdsOperationImpl implements DeleteEventsByIdsOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteEventsByIds(final ChangeContext<?> changeContext,
                                          final SqlConnection sqlConnection,
                                          final List<Long> ids) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, 0,
                """
                        update system.tab_event
                        set modified = $2, deleted = true
                        where id = any($1) and deleted = false
                        """,
                List.of(
                        ids.toArray(),
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> null,
                () -> null
        );
    }
}
