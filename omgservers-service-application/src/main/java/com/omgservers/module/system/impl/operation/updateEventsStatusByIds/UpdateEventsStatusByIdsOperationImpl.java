package com.omgservers.module.system.impl.operation.updateEventsStatusByIds;

import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeObject.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;
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
                        set modified = $3, status = $2
                        where id = any($1)
                        """,
                Arrays.asList(
                        ids.toArray(),
                        status,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> null,
                () -> logModelFactory.create(String.format("Events status were updated, " +
                        "count=%d, newStatus=%s", ids.size(), status))
        );
    }
}
