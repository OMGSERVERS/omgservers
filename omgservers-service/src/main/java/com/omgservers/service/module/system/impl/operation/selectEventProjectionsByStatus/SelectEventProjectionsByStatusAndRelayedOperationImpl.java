package com.omgservers.service.module.system.impl.operation.selectEventProjectionsByStatus;

import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.model.eventProjection.EventProjectionModel;
import com.omgservers.service.module.system.impl.mappers.EventProjectionModelMapper;
import com.omgservers.service.operation.selectList.SelectListOperation;
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
class SelectEventProjectionsByStatusAndRelayedOperationImpl
        implements SelectEventProjectionsByStatusAndRelayedOperation {

    final SelectListOperation selectListOperation;

    final EventProjectionModelMapper eventProjectionModelMapper;

    @Override
    public Uni<List<EventProjectionModel>> selectEventProjectionsByStatusAndRelayed(
            final SqlConnection sqlConnection,
            final EventStatusEnum status,
            final Boolean relayed,
            final int limit) {

        return selectListOperation.selectList(
                sqlConnection,
                0,
                """
                        select id, group_id
                        from system.tab_event
                        where status = $1 and relayed = $2
                        order by id asc
                        limit $3
                        """,
                Arrays.asList(status, relayed, limit),
                "Event projection",
                eventProjectionModelMapper::fromRow);
    }
}
