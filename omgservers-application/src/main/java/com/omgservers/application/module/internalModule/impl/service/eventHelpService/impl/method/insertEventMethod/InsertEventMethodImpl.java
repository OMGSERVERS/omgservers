package com.omgservers.application.module.internalModule.impl.service.eventHelpService.impl.method.insertEventMethod;

import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.operation.insertEventOperation.InsertEventOperation;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InsertEventMethodImpl implements InsertEventMethod {

    final InsertEventOperation insertEventOperation;

    @Override
    public Uni<Void> insertEvent(final InsertEventHelpRequest request) {
        InsertEventHelpRequest.validate(request);

        final var sqlConnection = request.getSqlConnection();
        final var event = request.getEvent();
        return insertEventOperation.insertEvent(sqlConnection, event);
    }
}
