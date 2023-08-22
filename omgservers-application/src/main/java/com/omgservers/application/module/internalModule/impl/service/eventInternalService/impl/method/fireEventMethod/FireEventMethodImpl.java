package com.omgservers.application.module.internalModule.impl.service.eventInternalService.impl.method.fireEventMethod;

import com.omgservers.application.Dispatcher;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.EventHelpService;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.operation.insertEventOperation.InsertEventOperation;
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

    final InsertEventOperation insertEventOperation;
    final CheckShardOperation checkShardOperation;

    final Dispatcher dispatcher;
    final PgPool pgPool;

    @Override
    public Uni<Void> fireEvent(final FireEventInternalRequest request) {
        FireEventInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var event = request.getEvent();
                    return pgPool.withTransaction(sqlConnection -> insertEventOperation
                                    .insertEvent(sqlConnection, event))
                            .invoke(inserted -> {
                                final var eventId = event.getId();
                                final var groupId = event.getGroupId();
                                dispatcher.addEvent(eventId, groupId);
                            });
                });
    }
}
