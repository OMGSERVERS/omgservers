package com.omgservers.module.system.impl.service.eventService.impl.method.viewEventsForRelay;

import com.omgservers.model.dto.system.ViewEventsForRelayRequest;
import com.omgservers.model.dto.system.ViewEventsForRelayResponse;
import io.smallrye.mutiny.Uni;

public interface ViewEventsForRelayMethod {
    Uni<ViewEventsForRelayResponse> viewEventsForRelay(ViewEventsForRelayRequest request);
}
