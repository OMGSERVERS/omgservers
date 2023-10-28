package com.omgservers.module.system.impl.service.eventService;

import com.omgservers.model.dto.internal.SyncEventRequest;
import com.omgservers.model.dto.internal.SyncEventResponse;
import com.omgservers.model.dto.internal.GetEventRequest;
import com.omgservers.model.dto.internal.GetEventResponse;
import com.omgservers.model.dto.internal.UpdateEventsRelayedFlagRequest;
import com.omgservers.model.dto.internal.UpdateEventsRelayedFlagResponse;
import com.omgservers.model.dto.internal.UpdateEventsStatusRequest;
import com.omgservers.model.dto.internal.UpdateEventsStatusResponse;
import com.omgservers.model.dto.internal.ViewEventsForRelayRequest;
import com.omgservers.model.dto.internal.ViewEventsForRelayResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface EventService {

    Uni<GetEventResponse> getEvent(@Valid GetEventRequest request);

    Uni<UpdateEventsRelayedFlagResponse> updateEventsRelayedFlag(@Valid UpdateEventsRelayedFlagRequest request);

    Uni<UpdateEventsStatusResponse> updateEventsStatus(@Valid UpdateEventsStatusRequest request);

    Uni<ViewEventsForRelayResponse> viewEventsForRelay(@Valid ViewEventsForRelayRequest request);

    Uni<SyncEventResponse> syncEvent(@Valid SyncEventRequest request);
}
