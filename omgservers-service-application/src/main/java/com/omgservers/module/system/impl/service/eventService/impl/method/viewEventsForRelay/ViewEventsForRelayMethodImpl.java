package com.omgservers.module.system.impl.service.eventService.impl.method.viewEventsForRelay;

import com.omgservers.model.dto.internal.ViewEventsForRelayRequest;
import com.omgservers.model.dto.internal.ViewEventsForRelayResponse;
import com.omgservers.model.event.EventStatusEnum;
import com.omgservers.module.system.impl.operation.selectEventProjectionsByStatus.SelectEventProjectionsByStatusAndRelayedOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewEventsForRelayMethodImpl implements ViewEventsForRelayMethod {

    final SelectEventProjectionsByStatusAndRelayedOperation selectEventProjectionsByStatusAndRelayedOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewEventsForRelayResponse> viewEventsForRelay(final ViewEventsForRelayRequest request) {
        final var limit = request.getLimit();
        return pgPool.withTransaction(sqlConnection -> selectEventProjectionsByStatusAndRelayedOperation
                        .selectEventProjectionsByStatusAndRelayed(
                                sqlConnection,
                                EventStatusEnum.NEW,
                                false,
                                limit))
                .map(ViewEventsForRelayResponse::new);
    }
}
