package com.omgservers.service.module.system.impl.service.eventService.impl.method.getEvent;

import com.omgservers.model.dto.system.GetEventRequest;
import com.omgservers.model.dto.system.GetEventResponse;
import com.omgservers.service.module.system.impl.operation.selectEvent.SelectEventOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetEventMethodImpl implements GetEventMethod {

    final SelectEventOperation selectEventOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetEventResponse> getEvent(GetEventRequest request) {
        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectEventOperation
                        .selectEvent(sqlConnection, id))
                .map(GetEventResponse::new);
    }
}
