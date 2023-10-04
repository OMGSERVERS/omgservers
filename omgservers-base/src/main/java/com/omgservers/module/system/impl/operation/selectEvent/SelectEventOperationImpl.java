package com.omgservers.module.system.impl.operation.selectEvent;

import com.omgservers.model.event.EventModel;
import com.omgservers.module.system.impl.mappers.EventModelMapper;
import com.omgservers.operation.selectObject.SelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

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
                        select id, created, modified, group_id, qualifier, relayed, body, status
                        from system.tab_event
                        where id = $1
                        limit 1
                        """,
                Collections.singletonList(id),
                "Event",
                eventModelMapper::createEvent);
    }
}
