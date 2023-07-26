package com.omgservers.application.module.internalModule.impl.service.eventHelpService.impl.method.insertEventMethod;

import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.operation.insertEventOperation.InsertEventOperation;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class InsertEventMethodImpl implements InsertEventMethod {

    final InsertEventOperation insertEventOperation;
    final GenerateIdOperation generateIdOperation;

    @Override
    public Uni<Void> insertEvent(final InsertEventHelpRequest request) {
        InsertEventHelpRequest.validate(request);

        final var sqlConnection = request.getSqlConnection();
        final var eventBody = request.getEventBody();
        final var event = EventModel.create(generateIdOperation.generateId(),
                eventBody.getGroupId(),
                eventBody.getQualifier(),
                eventBody);
        return insertEventOperation.insertEvent(sqlConnection, event);
    }
}
