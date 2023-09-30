package com.omgservers.module.system.impl.service.eventService;

import com.omgservers.dto.internal.FireEventRequest;
import com.omgservers.dto.internal.FireEventResponse;
import com.omgservers.dto.internal.GetEventRequest;
import com.omgservers.dto.internal.GetEventResponse;
import com.omgservers.dto.internal.UpdateEventsRelayedFlagRequest;
import com.omgservers.dto.internal.UpdateEventsRelayedFlagResponse;
import com.omgservers.dto.internal.UpdateEventsStatusRequest;
import com.omgservers.dto.internal.UpdateEventsStatusResponse;
import com.omgservers.dto.internal.ViewEventsForRelayRequest;
import com.omgservers.dto.internal.ViewEventsForRelayResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface EventService {

    Uni<GetEventResponse> getEvent(@Valid GetEventRequest request);

    Uni<UpdateEventsRelayedFlagResponse> updateEventsRelayedFlag(@Valid UpdateEventsRelayedFlagRequest request);

    Uni<UpdateEventsStatusResponse> updateEventsStatus(@Valid UpdateEventsStatusRequest request);

    Uni<ViewEventsForRelayResponse> viewEventsForRelay(@Valid ViewEventsForRelayRequest request);

    Uni<FireEventResponse> fireEvent(@Valid FireEventRequest request);
}
