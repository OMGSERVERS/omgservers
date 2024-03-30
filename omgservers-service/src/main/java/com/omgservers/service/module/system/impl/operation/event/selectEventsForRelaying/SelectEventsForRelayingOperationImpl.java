package com.omgservers.service.module.system.impl.operation.event.selectEventsForRelaying;

import com.omgservers.model.event.EventProjectionModel;
import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.service.module.system.impl.mappers.EventProjectionModelMapper;
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
class SelectEventsForRelayingOperationImpl implements SelectEventsForRelayingOperation {

    final SelectListOperation selectListOperation;

    final EventProjectionModelMapper eventProjectionModelMapper;

    @Override
    public Uni<List<EventProjectionModel>> selectEventsForRelaying(final SqlConnection sqlConnection,
                                                                   final int limit) {
        return selectListOperation.selectList(
                sqlConnection,
                0,
                """
                        select id, idempotency_key, created, modified, delayed, qualifier, status, deleted
                        from system.tab_event
                        where deleted = false and delayed <= $2 and status = $3
                        order by id asc
                        limit $1
                        for update
                        skip locked
                        """,
                List.of(
                        limit,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        EventStatusEnum.CREATED
                ),
                "Event projection",
                eventProjectionModelMapper::fromRow);
    }
}
