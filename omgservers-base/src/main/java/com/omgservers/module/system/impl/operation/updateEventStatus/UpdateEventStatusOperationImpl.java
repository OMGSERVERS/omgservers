package com.omgservers.module.system.impl.operation.updateEventStatus;

import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateEventStatusOperationImpl implements UpdateEventStatusOperation {

    private static final String SQL = """
                        
            """;

    final ExecuteChangeObjectOperation executeChangeObjectOperation;

    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> updateEventStatus(final ChangeContext<?> changeContext,
                                          final SqlConnection sqlConnection,
                                          final Long id,
                                          final EventStatusEnum newStatus) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, 0,
                """
                        update internal.tab_event
                        set status = $2
                        where id = $1
                        """,
                Arrays.asList(id, newStatus),
                () -> null,
                () -> logModelFactory.create("Index was deleted, id=" + id)
        );
    }
}
