package com.omgservers.service.server.event.operation;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventStatusEnum;
import com.omgservers.service.operation.server.SelectListOperation;
import com.omgservers.service.server.event.mapper.EventModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveEventsOperationImpl implements SelectActiveEventsOperation {

    final SelectListOperation selectListOperation;

    final EventModelMapper eventModelMapper;

    @Override
    public Uni<List<EventModel>> execute(final SqlConnection sqlConnection,
                                         final int limit) {
        return selectListOperation.selectList(
                sqlConnection,
                """
                        select id, idempotency_key, created, modified, qualifier, body, status, deleted
                        from $shard.tab_event
                        where deleted = false and status = $2
                        order by id asc
                        limit $1
                        """,
                List.of(
                        limit,
                        EventStatusEnum.CREATED
                ),
                "Event",
                eventModelMapper::fromRow);
    }
}
