package com.omgservers.module.system.impl.operation.updateEventsRelayedFlagByIds;

import com.omgservers.factory.LogModelFactory;
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
class UpdateEventsRelayedFlagByIdsOperationImpl implements UpdateEventsRelayedFlagByIdsOperation {

    final ChangeObjectOperation changeObjectOperation;

    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> updateEventsRelayedByIds(final ChangeContext<?> changeContext,
                                                 final SqlConnection sqlConnection,
                                                 final List<Long> ids,
                                                 final Boolean relayed) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, 0,
                """
                        update system.tab_event
                        set modified = $3, relayed = $2
                        where id = any($1)
                        """,
                Arrays.asList(
                        ids.toArray(),
                        relayed,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                () -> null,
                () -> logModelFactory.create(String.format("Events relayed flags were updated, " +
                        "count=%d, newValue=%s", ids.size(), relayed))
        );
    }
}
