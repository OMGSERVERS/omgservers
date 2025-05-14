package com.omgservers.service.master.task.impl.operation;

import com.omgservers.service.event.body.system.TaskDeletedEventBodyModel;
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
class DeleteTaskOperationImpl implements DeleteTaskOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final Long id) {
        return changeObjectOperation.execute(
                changeContext,
                sqlConnection,
                """
                        update $master.tab_task
                        set modified = $2, deleted = true
                        where id = $1 and deleted = false
                        """,
                List.of(id, Instant.now().atOffset(ZoneOffset.UTC)),
                () -> new TaskDeletedEventBodyModel(id),
                () -> null
        );
    }
}
