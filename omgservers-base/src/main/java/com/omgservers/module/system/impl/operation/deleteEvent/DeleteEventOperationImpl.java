package com.omgservers.module.system.impl.operation.deleteEvent;

import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteEventOperationImpl implements DeleteEventOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;

    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteEvent(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final Long id) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, 0,
                """
                        delete from internal.tab_event where
                        id = $1
                        """,
                Collections.singletonList(id),
                () -> null,
                () -> logModelFactory.create("Event was deleted, id=" + id)
        );
    }
}
