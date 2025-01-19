package com.omgservers.service.service.event.impl.method.relayEvents;

import com.omgservers.service.event.EventProjectionModel;
import com.omgservers.service.event.EventStatusEnum;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.service.event.component.eventEmitter.EventEmitter;
import com.omgservers.service.service.event.dto.RelayEventsRequest;
import com.omgservers.service.service.event.dto.RelayEventsResponse;
import com.omgservers.service.service.event.operation.selectEventsForRelaying.SelectEventsForRelayingOperation;
import com.omgservers.service.service.event.operation.updateEventsStatusByIds.UpdateEventsStatusByIdsOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RelayEventsMethodImpl implements RelayEventsMethod {

    final SelectEventsForRelayingOperation selectEventsForRelayingOperation;
    final UpdateEventsStatusByIdsOperation updateEventsStatusByIdsOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    final EventEmitter eventEmitter;

    @Override
    public Uni<RelayEventsResponse> relayEvents(final RelayEventsRequest request) {
        log.trace("Relay events, request={}", request);

        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) -> {
                    final var limit = request.getLimit();
                    return selectEventsForRelayingOperation.selectEventsForRelaying(sqlConnection, limit)
                            .flatMap(eventProjections -> {
                                if (eventProjections.isEmpty()) {
                                    return Uni.createFrom().item(Boolean.FALSE);
                                } else {
                                    return relayEvents(eventProjections)
                                            .flatMap(voidItem -> {
                                                final var ids = eventProjections.stream()
                                                        .map(EventProjectionModel::getId)
                                                        .toList();
                                                return updateEventsStatusByIdsOperation
                                                        .updateEventsStatusByIds(changeContext,
                                                                sqlConnection,
                                                                ids,
                                                                EventStatusEnum.RELAYED);
                                            })
                                            .replaceWith(Boolean.TRUE);
                                }
                            });
                })
                .map(ChangeContext::getResult)
                .map(RelayEventsResponse::new);
    }

    Uni<Void> relayEvents(final List<EventProjectionModel> eventProjections) {
        return Multi.createFrom().iterable(eventProjections)
                .onItem().transformToUniAndConcatenate(eventProjection ->
                        eventEmitter.sendEvent(eventProjection.getId()))
                .collect().asList()
                .replaceWithVoid();
    }
}
