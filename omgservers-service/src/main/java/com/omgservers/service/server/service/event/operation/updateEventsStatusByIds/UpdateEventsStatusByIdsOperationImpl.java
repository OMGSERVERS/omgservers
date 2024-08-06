package com.omgservers.service.server.service.event.operation.updateEventsStatusByIds;

import com.omgservers.schema.event.EventStatusEnum;
import com.omgservers.service.factory.lobby.LogModelFactory;
import com.omgservers.service.server.operation.changeObject.ChangeObjectOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
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
class UpdateEventsStatusByIdsOperationImpl implements UpdateEventsStatusByIdsOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> updateEventsStatusByIds(final ChangeContext<?> changeContext,
                                                final SqlConnection sqlConnection,
                                                final List<Long> ids,
                                                final EventStatusEnum status) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, 0,
                """
                        update system.tab_event
                        set modified = $2, status = $3
                        where id = any($1)
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
