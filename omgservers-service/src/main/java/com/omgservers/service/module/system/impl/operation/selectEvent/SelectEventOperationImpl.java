package com.omgservers.service.module.system.impl.operation.selectEvent;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.module.system.impl.mappers.EventModelMapper;
import com.omgservers.service.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectEventOperationImpl implements SelectEventOperation {

    final SelectObjectOperation selectObjectOperation;

    final EventModelMapper eventModelMapper;

    @Override
    public Uni<EventModel> selectEvent(final SqlConnection sqlConnection,
                                       final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                0,
                """
                        select id, idempotency_key, created, modified, delayed, qualifier, body, status, deleted
                        from system.tab_event
                        where id = $1
                        limit 1
                        """,
                List.of(id),
                "Event",
                eventModelMapper::fromRow);
    }
}
