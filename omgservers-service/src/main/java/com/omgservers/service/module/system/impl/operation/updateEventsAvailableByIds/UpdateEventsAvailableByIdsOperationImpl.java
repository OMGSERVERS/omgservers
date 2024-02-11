package com.omgservers.service.module.system.impl.operation.updateEventsAvailableByIds;

import com.omgservers.service.factory.LogModelFactory;
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
class UpdateEventsAvailableByIdsOperationImpl implements UpdateEventsAvailableByIdsOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> updateEventsAvailableByIds(final ChangeContext<?> changeContext,
                                                   final SqlConnection sqlConnection,
                                                   final List<Long> ids) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, 0,
                """
                        update system.tab_event
                        set
                            modified = $2,
                            available = $2 + attempts * INTERVAL '1 minute',
                            attempts = attempts + 1
                        where id = any($1)
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
