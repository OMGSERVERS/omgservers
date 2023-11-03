package com.omgservers.module.system.impl.service.eventService;

import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import com.omgservers.model.dto.system.GetEventRequest;
import com.omgservers.model.dto.system.GetEventResponse;
import com.omgservers.model.dto.system.UpdateEventsRelayedFlagRequest;
import com.omgservers.model.dto.system.UpdateEventsRelayedFlagResponse;
import com.omgservers.model.dto.system.UpdateEventsStatusRequest;
import com.omgservers.model.dto.system.UpdateEventsStatusResponse;
import com.omgservers.model.dto.system.ViewEventsForRelayRequest;
import com.omgservers.model.dto.system.ViewEventsForRelayResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface EventService {

    Uni<GetEventResponse> getEvent(@Valid GetEventRequest request);

    Uni<UpdateEventsRelayedFlagResponse> updateEventsRelayedFlag(@Valid UpdateEventsRelayedFlagRequest request);

    Uni<UpdateEventsStatusResponse> updateEventsStatus(@Valid UpdateEventsStatusRequest request);

    Uni<ViewEventsForRelayResponse> viewEventsForRelay(@Valid ViewEventsForRelayRequest request);

    Uni<SyncEventResponse> syncEvent(@Valid SyncEventRequest request);
}
