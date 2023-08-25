package com.omgservers.base.impl.service.eventHelpService.impl.method.fireEventMethod;

import com.omgservers.base.impl.service.eventHelpService.response.FireEventHelpResponse;
import com.omgservers.base.factory.EventModelFactory;
import com.omgservers.base.impl.service.eventHelpService.request.FireEventHelpRequest;
import com.omgservers.base.impl.service.eventInternalService.EventInternalService;
import com.omgservers.dto.internalModule.FireEventInternalRequest;
import com.omgservers.dto.internalModule.FireEventInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FireEventMethodImpl implements FireEventMethod {

    final EventInternalService eventInternalService;

    final EventModelFactory eventModelFactory;

    @Override
    public Uni<FireEventHelpResponse> fireEvent(final FireEventHelpRequest request) {
        FireEventHelpRequest.validate(request);

        final var body = request.getEventBody();
        final var event = eventModelFactory.create(body);

        final var fireEventInternalRequest = new FireEventInternalRequest(event);
        return eventInternalService.fireEvent(fireEventInternalRequest)
                .map(FireEventInternalResponse::getCreated)
                .map(FireEventHelpResponse::new);
    }
}
