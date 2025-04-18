package com.omgservers.service.server.event.operation;

import com.omgservers.service.event.EventStatusEnum;
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
class DeleteEventsAndUpdateStatusOperationImpl implements DeleteEventsAndUpdateStatusOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final List<Long> ids,
                                final EventStatusEnum status) {
        return changeObjectOperation.execute(
                changeContext,
                sqlConnection,
                """
                        update $shard.tab_event
                        set modified = $2, deleted = true, status = $3
                        where id = any($1) and deleted = false
                        """,
                List.of(
                        ids.toArray(),
                        Instant.now().atOffset(ZoneOffset.UTC),
                        status
                ),
                () -> null,
                () -> null
        );
    }
}
