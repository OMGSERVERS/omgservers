package com.omgservers.service.module.system.impl.operation.selectEventsForUpdate;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.module.system.impl.mappers.EventModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
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
class SelectEventsForUpdateOperationImpl implements SelectEventsForUpdateOperation {

    final SelectListOperation selectListOperation;
    final EventModelMapper eventModelMapper;

    @Override
    public Uni<List<EventModel>> selectEventsForUpdate(final SqlConnection sqlConnection,
                                                       final int limit) {
        return selectListOperation.selectList(
                sqlConnection,
                0,
                """
                        select id, created, modified, qualifier, body, available, attempts, deleted
                        from system.tab_event
                        where deleted = false and available <= $2
                        order by id asc
                        limit $1
                        for update
                        skip locked
                        """,
                List.of(
                        limit,
                        Instant.now().atOffset(ZoneOffset.UTC)
                ),
                "Event",
                eventModelMapper::createEvent);
    }
}
