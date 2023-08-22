package com.omgservers.application.module.internalModule.impl.service.eventInternalService.impl.method.fireEventMethod;

import com.omgservers.application.Dispatcher;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.EventHelpService;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.response.FireEventInternalResponse;
import com.omgservers.application.operation.changeOperation.ChangeOperation;
import com.omgservers.application.operation.upsertEventOperation.UpsertEventOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FireEventMethodImpl implements FireEventMethod {

    final EventHelpService eventHelpService;

    final UpsertEventOperation upsertEventOperation;
    final ChangeOperation changeOperation;

    final Dispatcher dispatcher;
    final PgPool pgPool;

    @Override
    public Uni<FireEventInternalResponse> fireEvent(final FireEventInternalRequest request) {
        FireEventInternalRequest.validate(request);

        final var event = request.getEvent();
        return changeOperation.change(request,
                        ((sqlConnection, shardModel) -> upsertEventOperation
                                .upsertEvent(sqlConnection, event)))
                .invoke(inserted -> {
                    final var eventId = event.getId();
                    final var groupId = event.getGroupId();
                    dispatcher.addEvent(eventId, groupId);
                })
                .map(FireEventInternalResponse::new);
    }
}
