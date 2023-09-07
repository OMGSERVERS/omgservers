package com.omgservers.module.system.impl.operation.selectNewEvents;

import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.module.system.impl.mappers.EventModelMapper;
import com.omgservers.operation.executeSelectList.ExecuteSelectListOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectNewEventsOperationImpl implements SelectNewEventsOperation {

    final ExecuteSelectListOperation executeSelectListOperation;

    final EventModelMapper eventModelMapper;

    @Override
    public Uni<List<EventModel>> selectNewEvents(final SqlConnection sqlConnection, final int limit) {
        return executeSelectListOperation.executeSelectList(
                sqlConnection,
                0,
                """
                        select id, created, modified, group_id, qualifier, body, status
                        from internal.tab_event
                        where status = $1
                        order by id asc
                        limit $2
                        """,
                Arrays.asList(EventStatusEnum.NEW, limit),
                "Event",
                eventModelMapper::createEvent);
    }
}
