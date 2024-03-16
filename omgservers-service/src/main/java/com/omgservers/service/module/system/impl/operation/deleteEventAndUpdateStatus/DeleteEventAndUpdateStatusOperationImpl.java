package com.omgservers.service.module.system.impl.operation.deleteEventAndUpdateStatus;

import com.omgservers.model.event.EventStatusEnum;
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
class DeleteEventAndUpdateStatusOperationImpl implements DeleteEventAndUpdateStatusOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> deleteEventAndUpdateStatus(final ChangeContext<?> changeContext,
                                                   final SqlConnection sqlConnection,
                                                   final Long id,
                                                   final EventStatusEnum status) {
        return changeObjectOperation.changeObject(
                changeContext, sqlConnection, 0,
                """
                        update system.tab_event
                        set modified = $2, deleted = true, status = $3
                        where id = $1 and deleted = false
                        """,
                List.of(
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        status
                ),
                () -> null,
                () -> null
        );
    }
}
