package com.omgservers.application.module.internalModule.impl.service.eventHelpService.impl.method.fireEventMethod;

import com.omgservers.application.module.internalModule.impl.operation.insertEventOperation.InsertEventOperation;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.FireEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.EventInternalService;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import com.omgservers.application.module.internalModule.model.event.EventModel;
import com.omgservers.application.operation.generateIdOperation.GenerateIdOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FireEventMethodImpl implements FireEventMethod {

    final EventInternalService eventInternalService;

    final GenerateIdOperation generateIdOperation;

    @Override
    public Uni<Void> fireEvent(final FireEventHelpRequest request) {
        FireEventHelpRequest.validate(request);

        final var eventBody = request.getEventBody();
        final var event = EventModel.create(generateIdOperation.generateId(),
                eventBody.getGroupId(),
                eventBody.getQualifier(),
                eventBody);

        final var fireEventInternalRequest = new FireEventInternalRequest(event);
        return eventInternalService.fireEvent(fireEventInternalRequest);
    }
}
