package com.omgservers.module.system.impl.service.eventService.impl.method.viewEventsForRelay;

import com.omgservers.dto.internal.ViewEventsForRelayRequest;
import com.omgservers.dto.internal.ViewEventsForRelayResponse;
import io.smallrye.mutiny.Uni;

public interface ViewEventsForRelayMethod {
    Uni<ViewEventsForRelayResponse> viewEventsForRelay(ViewEventsForRelayRequest request);
}
