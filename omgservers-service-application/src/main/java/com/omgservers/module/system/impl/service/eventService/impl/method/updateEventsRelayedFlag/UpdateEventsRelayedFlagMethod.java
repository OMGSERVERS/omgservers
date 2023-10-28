package com.omgservers.module.system.impl.service.eventService.impl.method.updateEventsRelayedFlag;

import com.omgservers.model.dto.internal.UpdateEventsRelayedFlagRequest;
import com.omgservers.model.dto.internal.UpdateEventsRelayedFlagResponse;
import io.smallrye.mutiny.Uni;

public interface UpdateEventsRelayedFlagMethod {
    Uni<UpdateEventsRelayedFlagResponse> updateEventsRelayedFlag(UpdateEventsRelayedFlagRequest request);
}